#

Summary; if you have binary (non-alphanumeric) data (or a significantly sized payload) to transmit, use multipart/form-data. Otherwise, use application/x-www-form-urlencoded.

The MIME types you mention are the two Content-Type headers for HTTP POST requests that user-agents (browsers) must support. The purpose of both of those types of requests is to send a list of name/value pairs to the server. Depending on the type and amount of data being transmitted, one of the methods will be more efficient than the other. To understand why, you have to look at what each is doing under the covers.

For application/x-www-form-urlencoded, the body of the HTTP message sent to the server is essentially one giant query string -- name/value pairs are separated by the ampersand (&), and names are separated from values by the equals symbol (=). An example of this would be:

MyVariableOne=ValueOne&MyVariableTwo=ValueTwo

According to the specification:  
>[Reserved and] non-alphanumeric characters are replaced by `%HH', a percent sign and two hexadecimal digits representing the ASCII code of the character

That means that for each non-alphanumeric byte that exists in one of our values, it's going to take three bytes to represent it. For large binary files, tripling the payload is going to be highly inefficient.

That's where multipart/form-data comes in. With this method of transmitting name/value pairs, each pair is represented as a "part" in a MIME message (as described by other answers). Parts are separated by a particular string boundary (chosen specifically so that this boundary string does not occur in any of the "value" payloads). Each part has its own set of MIME headers like Content-Type, and particularly Content-Disposition, which can give each part its "name." The value piece of each name/value pair is the payload of each part of the MIME message. The MIME spec gives us more options when representing the value payload -- we can choose a more efficient encoding of binary data to save bandwidth (e.g. base 64 or even raw binary).

Why not use multipart/form-data all the time? For short alphanumeric values (like most web forms), the overhead of adding all of the MIME headers is going to significantly outweigh any savings from more efficient binary encoding.

READ AT LEAST THE FIRST PARA HERE!

I know this is 3 years too late, but Matt's (accepted) answer is incomplete and will eventually get you into trouble. The key here is that, if you choose to use multipart/form-data, the boundary must not appear in the file data that the server eventually receives.

This is not a problem for application/x-www-form-urlencoded, because there is no boundary. x-www-form-urlencoded can also always handle binary data, by the simple expedient of turning one arbitrary byte into three 7BIT bytes. Inefficient, but it works (and note that the comment about not being able to send filenames as well as binary data is incorrect; you just send it as another key/value pair).

The problem with multipart/form-data is that the boundary separator must not be present in the file data (see RFC 2388; section 5.2 also includes a rather lame excuse for not having a proper aggregate MIME type that avoids this problem).

So, at first sight, multipart/form-data is of no value whatsoever( 丝毫，任何;) in any file upload, binary or otherwise. If you don't choose your boundary correctly, then you will eventually have a problem, whether you're sending plain text or raw binary - the server will find a boundary in the wrong place, and your file will be truncated, or the POST will fail.

The key is to choose an encoding and a boundary such that your selected boundary characters cannot appear in the encoded output. One simple solution is to use base64 (do not use raw binary). In base64 3 arbitrary bytes are encoded into four 7-bit characters, where the output character set is [A-Za-z0-9+/=] (i.e. alphanumerics, '+', '/' or '='). = is a special case, and may only appear at the end of the encoded output, as a single = or a double ==. Now, choose your boundary as a 7-bit ASCII string which cannot appear in base64 output. Many choices you see on the net fail this test - the MDN forms docs, for example, use "blob" as a boundary when sending binary data - not good. However, something like "!blob!" will never appear in base64 output.

[stackoverflow](https://stackoverflow.com/questions/4007969/application-x-www-form-urlencoded-or-multipart-form-data)
