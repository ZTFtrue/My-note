Exec=/usr/bin/google-chrome-stable %U --remote-debugging-port=9222
google-chrome-stable --remote-debugging-port=9222 --remote-debugging-host=0.0.0.0

## chrome.desktop
```
#!/usr/bin/env xdg-open
[Desktop Entry]
Version=1.0
Name=Google Chrome
# Only KDE 4 seems to use GenericName, so we reuse the KDE strings.
# From Ubuntu's language-pack-kde-XX-base packages, version 9.04-20090413.
GenericName=Web Browser
Exec=/usr/bin/google-chrome-stable %U --remote-debugging-port=9222
Terminal=false
Icon=google-chrome
Type=Application
Categories=Network;WebBrowser;
```