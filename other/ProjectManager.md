#

## Code Review

## 单元测试

### 监控服务

- prometheus/thanos 基础监控
- skywalking  链路监控
- elk
  - Elasticsearch
  - Logstash  主要是用来日志的搜集、分析、过滤日志的工具
  - Kibana Kibana可以为 Logstash 和 ElasticSearch 提供的日志分析友好的 Web 界面
  - Filebeat 轻量级的日志收集处理工具

## 灰度测试

正式发布前，给一部分人试用，逐步扩大试用人群，以便及时发现和纠正其中的问题.

灰度期: 灰度测试开始到结束期间的这一段时间，称为灰度期.
