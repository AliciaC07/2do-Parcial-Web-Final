# This is a sample Python script.
from zeep import Client
# from suds.client import Client
import json
from requests import *
import logging.config


# Press Shift+F10 to execute it or replace it with your code.
# Press Double Shift to search everywhere for classes, files, tool windows, actions, and settings.


# Press the green button in the gutter to run the script.
class User:
    def __init__(self, id, userName, password, rol, active, urls):
        self.id = id
        self.userName = userName
        self.password = password
        self.rol = rol
        self.active = active
        self.urls = urls

    def __int__(self):
        pass


class Url:
    def __init__(self, id, originalUrl, cuttedUrl, user, active, qrCode, dateAdded, visits):
        self.id = id
        self.orginalUrl = originalUrl
        self.cuttedUrl = cuttedUrl
        self.user = user
        self.active = active
        self.qrCode = qrCode
        self.dateAdded = dateAdded
        self.visits = visits

    def __int__(self):
        pass


if __name__ == '__main__':
    url = "http://localhost:7001/ws/SoapShortly?wsdl"
    user = User(1, "alicruz0703@gmail.com", "123", "Admin", True, [])
    client = Client(url)
    client.settings.strict = False
    client.settings.xml_huge_tree = True
    newUrl = client.service.createUrl("https://www.youtube.com/watch?v=j0b84JCoenI",1)
    print(newUrl)
    listAll = client.service.getUrlsByUser(1)
    print(listAll)
    #for item in listAll:
     #   print(item)

    clicks = client.service.getClicks(1)
    print(clicks)
    clicksInfo = client.service.getVisitInfo(1)
    print(clicksInfo)
# See PyCharm help at https://www.jetbrains.com/help/pycharm/
