# Generated by the gRPC Python protocol compiler plugin. DO NOT EDIT!
"""Client and server classes corresponding to protobuf-defined services."""
import grpc

import gRPC_pb2 as gRPC__pb2


class UrlServiceStub(object):
    """Missing associated documentation comment in .proto file."""

    def __init__(self, channel):
        """Constructor.

        Args:
            channel: A grpc.Channel.
        """
        self.getAllUrl = channel.unary_unary(
                '/url.UrlService/getAllUrl',
                request_serializer=gRPC__pb2.UrlRequest.SerializeToString,
                response_deserializer=gRPC__pb2.UrlResponse.FromString,
                )
        self.createUrl = channel.unary_unary(
                '/url.UrlService/createUrl',
                request_serializer=gRPC__pb2.CreateUrlRequest.SerializeToString,
                response_deserializer=gRPC__pb2.CreateUrlResponse.FromString,
                )
        self.getUrlInfo = channel.unary_unary(
                '/url.UrlService/getUrlInfo',
                request_serializer=gRPC__pb2.UrlInfoRequest.SerializeToString,
                response_deserializer=gRPC__pb2.UrlInfoResponse.FromString,
                )


class UrlServiceServicer(object):
    """Missing associated documentation comment in .proto file."""

    def getAllUrl(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def createUrl(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def getUrlInfo(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')


def add_UrlServiceServicer_to_server(servicer, server):
    rpc_method_handlers = {
            'getAllUrl': grpc.unary_unary_rpc_method_handler(
                    servicer.getAllUrl,
                    request_deserializer=gRPC__pb2.UrlRequest.FromString,
                    response_serializer=gRPC__pb2.UrlResponse.SerializeToString,
            ),
            'createUrl': grpc.unary_unary_rpc_method_handler(
                    servicer.createUrl,
                    request_deserializer=gRPC__pb2.CreateUrlRequest.FromString,
                    response_serializer=gRPC__pb2.CreateUrlResponse.SerializeToString,
            ),
            'getUrlInfo': grpc.unary_unary_rpc_method_handler(
                    servicer.getUrlInfo,
                    request_deserializer=gRPC__pb2.UrlInfoRequest.FromString,
                    response_serializer=gRPC__pb2.UrlInfoResponse.SerializeToString,
            ),
    }
    generic_handler = grpc.method_handlers_generic_handler(
            'url.UrlService', rpc_method_handlers)
    server.add_generic_rpc_handlers((generic_handler,))


 # This class is part of an EXPERIMENTAL API.
class UrlService(object):
    """Missing associated documentation comment in .proto file."""

    @staticmethod
    def getAllUrl(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(request, target, '/url.UrlService/getAllUrl',
            gRPC__pb2.UrlRequest.SerializeToString,
            gRPC__pb2.UrlResponse.FromString,
            options, channel_credentials,
            insecure, call_credentials, compression, wait_for_ready, timeout, metadata)

    @staticmethod
    def createUrl(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(request, target, '/url.UrlService/createUrl',
            gRPC__pb2.CreateUrlRequest.SerializeToString,
            gRPC__pb2.CreateUrlResponse.FromString,
            options, channel_credentials,
            insecure, call_credentials, compression, wait_for_ready, timeout, metadata)

    @staticmethod
    def getUrlInfo(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(request, target, '/url.UrlService/getUrlInfo',
            gRPC__pb2.UrlInfoRequest.SerializeToString,
            gRPC__pb2.UrlInfoResponse.FromString,
            options, channel_credentials,
            insecure, call_credentials, compression, wait_for_ready, timeout, metadata)
