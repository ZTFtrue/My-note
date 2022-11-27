# Java 软件应用

## java doc

Java apache 文档操作, document, office

```java
XWPFDocument docx = new XWPFDocument(new FileInputStream("/home/ztftrue/Documents/idea-java/fixdoc/1.docx"));
String colorRed = "ff2b00";// 非单词
ArrayList<XWPFParagraph> xwpfParagraphList = new ArrayList<>(docx.getParagraphs());
```

```xml
    <dependency>
        <groupId>org.apache.poi</groupId>
        <artifactId>poi</artifactId>
        <version>4.1.1</version>
    </dependency>
```

## 爬虫

java 爬虫

```xml
<dependency>
    <groupId>net.sourceforge.htmlunit</groupId>
    <artifactId>htmlunit</artifactId>
    <version>2.53.0</version>
</dependency>
```
