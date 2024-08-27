#

## 创建 `desktop` 文件

在`/usr/share/applications` 创建 `desktop` 文件

例如:

创建 `/usr/share/applications/test-image.desktop` 文件

修改文件内容

```desktop
[Desktop Entry]
Name=TestImage
Exec=/home/ztftrue/Documents/nomacs/ImageLounge/build/nomacs %U
Type=Application

MimeType=image/gif;image/jpeg;image/png;image/bmp;image/tiff;image/x-eps;image/x-ico;image/x-portable-bitmap;image/x-portable-graymap;image/x-portable-pixmap;image/x-xbitmap;image/x-xpixmap;image/bmp;image/gif;image/jpeg;image/jpg;image/pjpeg;image/png;image/tiff;image/x-bmp;image/x-gray;image/x-icb;image/x-ico;image/x-png;image/x-portable-anymap;image/x-portable-bitmap;image/x-portable-graymap;image/x-portable-pixmap;image/x-xbitmap;image/x-xpixmap;image/x-pcx;image/svg+xml;image/svg+xml-compressed;image/vnd.wap.wbmp;image/x-icns;x-scheme-handler/subl;x-scheme-handler/subl;
```

解析:

`Exec` 是要执行文件的路径, %U 和 %F 都表示可以传参, 其中 `%U` 可以接收回收站的参数.

`MimeType` 是支持的协议或者文件类型,比如`x-scheme-handler/subl` ,就可以通过命令`xdg-open subl://` 打开.

## 创建建`.appdata.xml`文件

在 `/usr/share/metainfo/` 创建 `.appdata.xml` 文件

例如:

创建 `/usr/share/metainfo/test-mage.appdata.xml` 文件

修改文件内容

```xml
<?xml version="1.0" encoding="UTF-8"?>
<component type="desktop">
  <id>test-image.desktop</id>
  <metadata_license>CC0-1.0</metadata_license>
  <project_license>GPL-2.0+ and GFDL-1.3</project_license>
  <name>test-image</name>
  <name xml:lang="zh_TW">GNOME 之眼</name>
  <name xml:lang="zh_CN">GNOME 之眼</name>
  <description>
    <p xml:lang="fi"> </p>
  </description>
  <url type="homepage">https://wiki.gnome.org/Apps/EyeOfGnome</url>
  <screenshots>
    <screenshot type="default">
      <image>https://gitlab.gnome.org/GNOME/eog/raw/master/data/screenshot.png</image>
    </screenshot>
  </screenshots>
  <update_contact>eog-list@gnome.org</update_contact>
  <kudos>
    <kudo>AppMenu</kudo>
    <kudo>ModernToolkit</kudo>
    <kudo>UserDocs</kudo>
  </kudos>
  <releases>
    <release version="40.3" date="2021-08-14">
      <description>
        <p>Eye of GNOME 40.3 is the latest stable version of Eye of GNOME, and it contains all the features and bugfixes introduced since our 3.38 release.</p>
      </description>
    </release>
  </releases>
  <project_group>GNOME</project_group>
  <translation type="gettext">eog</translation>
  <content_rating type="oars-1.1">
    <content_attribute id="violence-cartoon">none</content_attribute>
  </content_rating>
</component>
```

重点在 `name` 和 `id`

`name` 就是右键打开的名称, `id` 就是对应的 `desktop` 文件.

## 附注

qt也支持在linux 的移动到回收站

```c++
QFile file(filePath);
return file.moveToTrash();
```

## 

desktop 文件名最好是要求和应用程序名一样, 这样可以在某些桌面看到应用运行的指示器.

## 参考

<https://askubuntu.com/questions/527166/how-to-set-subl-protocol-handler-with-unity>

<https://wiki.archlinux.org/title/desktop_entries>
