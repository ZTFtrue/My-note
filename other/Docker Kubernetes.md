#

## Docker


## Kubernetes


Kubernetes 多租户有两种方案:多命名空间和多集群。
 
多集群方案分为:

- 共享控制方案:apiserver/scheduler/controller-manager 统一部署,每个集群只有node 是单独部署的,node 可以直接购买 VM. 优点：节约控制节点资源,计算节点资源隔离
- VirtualNode 方案:为了解决node 独占带来的资源浪费,使用共享控制面+VirtualNode.  
- ClusterinCluster:创建一个母集群,然后虚拟出多个子集群(每个namespace 虚拟出一个子集群),优点：子集群之间可以共享节点,提高资源利用率。