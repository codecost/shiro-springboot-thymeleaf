<center>shrio</center>
认证，授权，加密，会话管理，web集成，缓存等。

### shrio架构

###### subject

应用代码直接交互的对象是Subject，subject代表了当前的用户，这个用户不代表是人，与当前应用交互的所有东西都是subject，如网络爬虫，机器人等。与Subject的所有交互都会委托给SecurityManager，Subject其实是一个门面，SecurityManager才是实际的执行者。

###### SecurityManager

安全管理器，即所有与安全相关的操作都会与securitymanager产生交互，并且它管理着所有的subject，它是shrio的核心，相当于springmvc的dispatcherservlet。

###### Realm

shrio从realm获取安全数据（如用户，角色，权限等），就是说springmanager要验证用户身份，那么它需要从realm获取相应的用户进行比较，来确定用户的身份是否合法，也需要从realm得到用户相应的角色，权限，进行验证用户的操作是否能进行，可以看作是datasource。

```java
//获取当前的用户对象
Subject currentUser = SecurityUtils.getSubject();

// 通过当前用户拿到session
Session session = currentUser.getSession();

// 判断当前用户是否被认证
if (!currentUser.isAuthenticated())
currentUser.getPrincipal()
currentUser.isPermitted
currentUser.logout();
```

SpringBoot集成shiro

##### 连接mybatis

###### 为什么要设置varchar（255）

很多时候我们设置varchar(255)都习以为常了，甚至我还遇到过有人以为varchar不能设置超过255的人。其实varchar没有明确最大长度，然后有人说那65535字节(bytes)不是吗？

但是事实上如果你用的 utf8 编码的话按理最大可以设置到 varchar(21845)，但是其实一般都会说你超出。其实MySQL要求一个行定义长度不能超过 65535 bytes（所有字符串类型字段包括其字段名称占用空间都计算在内， text、blob等大字段类型除外)。

如下图我先设置了个21842成功了，后面加个长度为3的varchar都会报错，所有如果一个表有很多varchar字段的时候，不应该把varchar设置的特别大，会影响后面的字段
这里写图片描述

言归正传那为什么我们会经常性设置成varchar(255)呢？

首先我们要知道一个概念：InnoDB存储引擎的表索引的前缀长度最长是767字节(bytes)

你如果需要建索引，就不能超过 767 bytes；utf8编码时 255*3=765bytes ,恰恰是能建索引情况下的最大值。

如果像lavavel5.3往后 使用的是utf8mb4编码，默认字符长度则应该是 767除以4向下取整，也就是191。

总结：varchar(255) 不是最优的字符长度，最优还是应该根据实际需要的来。但是这是一个保证你能少出错的一个很好的默认值

##### shrio授权实现

### git连接顺序

##### 1.创建一个和远程仓库同名的文件夹

##### 2.git init

##### 3.**连接远程仓库**(下面两种方式都可以)

```
git remote add origin git@github.com:yourName/repositoryname.git
git remote add origin https://github.com/yourName/repositoryname.git
```

##### 4.**从远程仓库pull文件**(若远程仓库没有文件，直接执行下列步骤)

```
git pull origin master
```

##### 5. **将本地文件push到远程仓库**(若没有文件则手动创建) ，下列命令选择执行

```
git status　　　　　　　　　　查看工作目录的状态

git add <file>　　　　　　　　将文件添加到暂存区

git commit -m "commnet" 　　提交更改,添加备注信息(此时将暂存区的信息提交到本地仓库)

git push origin master 　　 将本地仓库的文件push到远程仓库(若 push 不成功，可加 -f 进行强推操作)
```