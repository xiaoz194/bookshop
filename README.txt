图书销售网站
v1.0  2020/4/28
主要功能需求：
前端部分：
1、图书信息模块（浏览最新图书、支持分类等）
2、购物车模块（支持查看并汇总已选图书信息及价格，添加购物车）；
3、订单模块；
4、用户注册登录。
后端部分：1、管理员登录管理系统；2、图书信息管理模块；
3、用户信息管理模块；
4、销售信息管理模块。
数据库设计：
user: id username password address tell
admin: id username password
book:id bookname author desc pic money cid collected(0 1)
category:id cname  所属类别
ordr:id bid(需要关联 bookname，pic,money) uid(关联username,address,tell) time(下单日期)
        count(数量) flag(确认订单的标志位 0 1)

采用技术 springboot+mybatis+thymeleaf    java版本jdk8 数据库使用mysql
使用开发工具 idea  如果电脑上没有idea，在官网下载一个就可以。
数据库和mybatis等配置文件在application.yml文件中，启动前请先在该yml文件中把数据库账号密码信息修改为自己的账号密码。
配置完jdk和数据库信息后，直接启动项目即可。
管理员端入口：http://localhost:8080/page/toLogin
用户端入口：http://localhost:8080/shop/toHomeIndex
#######################################################################
add log：v1.1
1.代码写的太乱，需要重构
2.修复bug：
（1）用户点击登录，登录成功后，首页的导航栏中的图书分类消失。            解决
（2）订单中显示的是所有的订单信息而不是目前登录的用户的订单信息。        解决
（3）订单生成后的总价不显示。                                          解决
（4）当没有选择商品数量点击加入购物车也可以操作，之后系统报错。          解决
3.新增用户个人中心的模块，包括用户的个人资料和修改个人资料
4.新增一些交互性的功能，如用户密码错误时给予提示等。提升用户体验
5.当用户在购物车下单后不应该直接生成订单而是应该进入订单页，内容包括（可以修改）自己的收货地址及商品信息。


