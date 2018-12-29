import re

def checkPCorMobile(info):
     phoneheads=["Android","windowphone","iPhone"]

     count=0;
     for data in phoneheads:
         print("+++",data)
         val = re.search(data,info)
         print("***",val)
         # 至少为1

         print("///////",count)

         if val is None:
             count=0
             #return "PC"
         else:
             count = count + 1

             #return "Mobile"
             return count
     print(count)
     return count


#aa=checkPCorMobile("Mozilla/5.0 (iPhone; CPU iPhone OS 11_1_2 like Mac OS X) AppleWebKit/604.3.5 (KHTML, like Gecko) Mobile/15B202 Html5Plus/1.0")

#print("---",aa)