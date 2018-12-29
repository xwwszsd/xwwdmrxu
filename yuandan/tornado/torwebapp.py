import tornado.web
import tornado.ioloop
import suds
from suds import client
from phoneutil.phone import *
from phoneutil.cacheutils import *

class IndexHandler(tornado.web.RequestHandler):
    #get请求  self:当前对象
    def get(self):
        print("接收到用户的GET请求")
        #写消息
        self.render("login.html",failmsg=None)

class User(tornado.web.RequestHandler):
    def post(self):
        print("接收到用户的post请求")
        method=self.get_argument("action")
        print("method-->", method)
        if method == "login":

            username=self.get_argument("username")
            userpwd=self.get_argument("userpwd")
            print(username, userpwd)
            url="http://127.0.0.1:8100/userdataservice/user?wsdl"

            server=suds.client.Client(url)
            mesg=server.service.checklogin(username,userpwd)
            print("mesg-->", mesg)

            # 怎么来区分是浏览器还是手机的请求
            # 得到请求的头
            headerinfo=self.request.headers
            print(headerinfo)
            hinfo = headerinfo["User-Agent"]

            val = checkPCorMobile(hinfo)
            print("请求的头的信息为:", hinfo)
            print("val", val)

            jsonDatas = ""
            if(mesg=="成功"):
                # 菜单数据是不经常变化的，我们应从缓存中获取，不应每次从数据库中响应,
                # 减少对数据库服务器的负载。 1.0

                # 2.0  从缓存中获取策略  内部缓存
                cacheService = CacheService()
                jsonDatas = cacheService.getMenuData("tmenudata")
                if val == 0:


                  self.render("index.html",contentdata=jsonDatas)
                else:
                   self.finish({"mesg":"success","contentdata":jsonDatas})
            else:
                if val == 0:
                    self.render("login.html",failmsg=mesg)
                else:
                    self.finish({"mesg":"faile"})
        elif method=="register":
            self.render("register.html")

class Antvhead(tornado.web.RequestHandler):
    def post(self):
        print("antv业务报表功能")
        method=self.get_argument("datas")
        print("method-->", method)

        count=0
        if method=="queryfruitadd":
            url = "http://127.0.0.1:8100/userdataservice/user?wsdl"
            server=suds.client.Client(url)
            data=server.service.antv1()
            print("data-->", data)
            print(type(data))
            jsdata=json.loads(data)
            print("jsdata-->", jsdata)
            self.finish({"jsdata":jsdata})
            count=count+1




#设置配置项
setting={"template_path":"templates",
         "static_path": "static"}
#创建一个应用对象
#包含路由
app=tornado.web.Application([(r'/',IndexHandler),
                             (r'/user',User),
                             (r'/antv',Antvhead)],**setting)

if __name__=="__main__":
    #绑定一个监听端口，内网穿透保持一致
    app.listen(8090)
    #启动web程序，开始监听端口的链接
    tornado.ioloop.IOLoop.current().start()