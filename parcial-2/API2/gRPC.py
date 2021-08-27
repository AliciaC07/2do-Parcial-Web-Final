import json

import grpc
import gRPC_pb2
import gRPC_pb2_grpc

if __name__ == '__main__':
    channel = grpc.insecure_channel('localhost:50051')
    api = gRPC_pb2_grpc.UrlServiceStub(channel)

    create = api.createUrl(gRPC_pb2.CreateUrlRequest(idUser=1,
                                                     originalUrl="https://stackoverflow.com/questions/57621100/how-to"
                                                                 "-add-message-type-as-object-in-protobuf-grpc-proto3"
                                                                 "-syntax"))
    print(create.url)

    getAll = api.getAllUrl(gRPC_pb2.UrlRequest(id=1))
    print(getAll.urls)

    response = api.getUrlInfo(gRPC_pb2.UrlInfoRequest(id=1))
    print(response.infoUrl)
