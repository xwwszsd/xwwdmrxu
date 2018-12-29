import suds
from  suds  import  client
from phoneutil.phone import *
import json

class CacheService():

    #全局静态数据
    cachedata={}

    #__私有变量
    __number=10;

    def __init__(self):
        print("-------产生一个对象初始化一次---------")
        #定义一个缓存的属性 {键:值}
        #self.cachedata={}

    def   getMenuData(self,key):

           #判断这个key在不在    self.cachedata
          if  key  in self.cachedata:
              print("缓存中有数据**********")
              return  self.cachedata[key]
          else:
              print("缓存中没有数据--------------------")
              url = "http://127.0.0.1:8100/userdataservice/user?wsdl"
              server = suds.client.Client(url)
              data = server.service.menu()
              print("data-->", data)
              print(type(data))
              jsonDatas = json.loads(data)
              print("jsonDatas-->", jsonDatas)
              self.cachedata[key]=jsonDatas
              return jsonDatas