import json
from types import SimpleNamespace

import requests

# http://localhost:7001/api
if __name__ == '__main__':
    login = requests.post('http://localhost:7001/login-rest', json={"userName": "alicruz0703@gmail.com",
                                                                    "password": "123"})
    userJson = login.json()
    user = json.loads(json.dumps(login.json()), object_hook=lambda d: SimpleNamespace(**d))
    # print(user)
    userJson.pop("token")
    userJson.pop("type")
    headers = {"Content-type": "application/json", 'Authorization': 'Bearer ' + login.json()["token"]}
    createUrl = requests.post('http://localhost:7001/api/url-create',
                              json={"originalUrl": "https://towardsdatascience"
                                                   ".com/restful-apis-in"
                                                   "-python-121d3763a0e4",
                                    "user": {"id": userJson["id"],
                                             "userName": userJson["userName"],
                                             "password": userJson["password"],
                                             "rol": userJson["rol"],
                                             "active": True}}, headers=headers)
    print(createUrl.json())

    visits = requests.get("http://localhost:7001/api/visits-info/2", headers=headers)
    print(visits.json())
    urls = requests.get("http://localhost:7001/api/urls/1", headers=headers)
    print(urls.json())
