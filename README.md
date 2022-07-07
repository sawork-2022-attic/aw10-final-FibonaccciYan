# aw10-final


Please develop a **fully functional** online purchase order system.

- It should have a superb collection of goods merchandises
- Customer can browse/search for merchandises, add selected one into his shopping cart and checkout to complete a transaction.
- User can get delivery status updates continuously.

The system should be of a **reactive architecture**, which means it should be 

-  Responsive: it should response to the user request timely.
-  Resilient: it should not be easily broken down.
-  Elastic: it should be flexible to scale out.
-  Message Driven: it should has loosely coupled components that communicates with each other asynchronously.


Please design tests/experiements to demostrate that your system fulfills such requirements as stated in [The Reactive Manifesto](https://www.reactivemanifesto.org)

**Submit your codes/documents/tests/experiements of your system.**

---

## 消息驱动

系统中互相通信的部分为： pos-carts服务器从 pos-products 服务器中按 id 查找商品信息 pos-carts 服务器向 pos-order 服务器请求结算并生成订单；

## 可靠性

### 微服务架构

实现如下微服务架构：

![Micropos](./images/Micropos.svg)

使用了 Eureka 将各项微服务注册到 Eureka 服务器，因此除了 pos-discovery 服务器和 pos-gateway 服务器以外，其余服务器均可以多开且相互独立；

## 弹性

由于采用了微服务架构，易于水平拓展，所以整个系统弹性较好；

## 拓展性

对于请求和响应的处理操作具有高可拓展性：

+ 使用了 WebFlux，支持异步消息；
+ 在网关加入了 IntegrationFlows；
+ 可以在 RabbitMq 消息队列中加入拦截器，实现在 channel 中对消息进行修改；

## 压力测试

### 测试脚本

内容：

1. 列出所有商品信息；
2. 列出 id 为 13284888 的商品信息；
3. 列出 id 为 13122155 的商品信息；
4. 展示购物车信息；
5. 向购物车添加一项 id 为 13122155 的商品；
6. 展示购物车信息；
7. 结算并创建订单；
8. 查询配送状态；

详情请见 `/gatlingTest/LoadTestSimulation.java`；

### 结果

![gatling test result](./images/gatlingTest.png)

从上图可见测试性能较好，主要延时来源于查询京东数据库、结算并创建订单和查询配送状态。
