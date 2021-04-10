# Systemd timers

Systemd also has the possibility to run specific tasks at specific times or events. called Timers. You need to create 2 unit files:

> /etc/systemd/system/certbotrenew.service

```systemd
[Unit]
Description=Nextcloud Preview Generator

[Service]
Type=oneshot
User=root
ExecStart=certbot renew&&systemctl restart nginx.service

[Install]
WantedBy=basic.target
```

> /etc/systemd/system/certbotrenew.timer

```systemd
[Unit]
Description=Run 3w renew and start 10min renew

[Timer]
OnBootSec=10min
OnUnitActiveSec=1w
Unit=certbotrenew.service

[Install]
WantedBy=timers.target
```

```sh
systemd reload systemctl daemon-reload
systemctl enable --now certbotrenew.timer
```