package url;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.39.0)",
    comments = "Source: Url.proto")
public final class UrlServiceGrpc {

  private UrlServiceGrpc() {}

  public static final String SERVICE_NAME = "url.UrlService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<url.Url.UrlRequest,
      url.Url.UrlResponse> getGetAllUrlMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getAllUrl",
      requestType = url.Url.UrlRequest.class,
      responseType = url.Url.UrlResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<url.Url.UrlRequest,
      url.Url.UrlResponse> getGetAllUrlMethod() {
    io.grpc.MethodDescriptor<url.Url.UrlRequest, url.Url.UrlResponse> getGetAllUrlMethod;
    if ((getGetAllUrlMethod = UrlServiceGrpc.getGetAllUrlMethod) == null) {
      synchronized (UrlServiceGrpc.class) {
        if ((getGetAllUrlMethod = UrlServiceGrpc.getGetAllUrlMethod) == null) {
          UrlServiceGrpc.getGetAllUrlMethod = getGetAllUrlMethod =
              io.grpc.MethodDescriptor.<url.Url.UrlRequest, url.Url.UrlResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getAllUrl"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  url.Url.UrlRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  url.Url.UrlResponse.getDefaultInstance()))
              .setSchemaDescriptor(new UrlServiceMethodDescriptorSupplier("getAllUrl"))
              .build();
        }
      }
    }
    return getGetAllUrlMethod;
  }

  private static volatile io.grpc.MethodDescriptor<url.Url.CreateUrlRequest,
      url.Url.CreateUrlResponse> getCreateUrlMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "createUrl",
      requestType = url.Url.CreateUrlRequest.class,
      responseType = url.Url.CreateUrlResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<url.Url.CreateUrlRequest,
      url.Url.CreateUrlResponse> getCreateUrlMethod() {
    io.grpc.MethodDescriptor<url.Url.CreateUrlRequest, url.Url.CreateUrlResponse> getCreateUrlMethod;
    if ((getCreateUrlMethod = UrlServiceGrpc.getCreateUrlMethod) == null) {
      synchronized (UrlServiceGrpc.class) {
        if ((getCreateUrlMethod = UrlServiceGrpc.getCreateUrlMethod) == null) {
          UrlServiceGrpc.getCreateUrlMethod = getCreateUrlMethod =
              io.grpc.MethodDescriptor.<url.Url.CreateUrlRequest, url.Url.CreateUrlResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "createUrl"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  url.Url.CreateUrlRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  url.Url.CreateUrlResponse.getDefaultInstance()))
              .setSchemaDescriptor(new UrlServiceMethodDescriptorSupplier("createUrl"))
              .build();
        }
      }
    }
    return getCreateUrlMethod;
  }

  private static volatile io.grpc.MethodDescriptor<url.Url.UrlInfoRequest,
      url.Url.UrlInfoResponse> getGetUrlInfoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getUrlInfo",
      requestType = url.Url.UrlInfoRequest.class,
      responseType = url.Url.UrlInfoResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<url.Url.UrlInfoRequest,
      url.Url.UrlInfoResponse> getGetUrlInfoMethod() {
    io.grpc.MethodDescriptor<url.Url.UrlInfoRequest, url.Url.UrlInfoResponse> getGetUrlInfoMethod;
    if ((getGetUrlInfoMethod = UrlServiceGrpc.getGetUrlInfoMethod) == null) {
      synchronized (UrlServiceGrpc.class) {
        if ((getGetUrlInfoMethod = UrlServiceGrpc.getGetUrlInfoMethod) == null) {
          UrlServiceGrpc.getGetUrlInfoMethod = getGetUrlInfoMethod =
              io.grpc.MethodDescriptor.<url.Url.UrlInfoRequest, url.Url.UrlInfoResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getUrlInfo"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  url.Url.UrlInfoRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  url.Url.UrlInfoResponse.getDefaultInstance()))
              .setSchemaDescriptor(new UrlServiceMethodDescriptorSupplier("getUrlInfo"))
              .build();
        }
      }
    }
    return getGetUrlInfoMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static UrlServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UrlServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UrlServiceStub>() {
        @java.lang.Override
        public UrlServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UrlServiceStub(channel, callOptions);
        }
      };
    return UrlServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static UrlServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UrlServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UrlServiceBlockingStub>() {
        @java.lang.Override
        public UrlServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UrlServiceBlockingStub(channel, callOptions);
        }
      };
    return UrlServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static UrlServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UrlServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UrlServiceFutureStub>() {
        @java.lang.Override
        public UrlServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UrlServiceFutureStub(channel, callOptions);
        }
      };
    return UrlServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class UrlServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void getAllUrl(url.Url.UrlRequest request,
        io.grpc.stub.StreamObserver<url.Url.UrlResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetAllUrlMethod(), responseObserver);
    }

    /**
     */
    public void createUrl(url.Url.CreateUrlRequest request,
        io.grpc.stub.StreamObserver<url.Url.CreateUrlResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateUrlMethod(), responseObserver);
    }

    /**
     */
    public void getUrlInfo(url.Url.UrlInfoRequest request,
        io.grpc.stub.StreamObserver<url.Url.UrlInfoResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetUrlInfoMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetAllUrlMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                url.Url.UrlRequest,
                url.Url.UrlResponse>(
                  this, METHODID_GET_ALL_URL)))
          .addMethod(
            getCreateUrlMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                url.Url.CreateUrlRequest,
                url.Url.CreateUrlResponse>(
                  this, METHODID_CREATE_URL)))
          .addMethod(
            getGetUrlInfoMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                url.Url.UrlInfoRequest,
                url.Url.UrlInfoResponse>(
                  this, METHODID_GET_URL_INFO)))
          .build();
    }
  }

  /**
   */
  public static final class UrlServiceStub extends io.grpc.stub.AbstractAsyncStub<UrlServiceStub> {
    private UrlServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UrlServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UrlServiceStub(channel, callOptions);
    }

    /**
     */
    public void getAllUrl(url.Url.UrlRequest request,
        io.grpc.stub.StreamObserver<url.Url.UrlResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAllUrlMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void createUrl(url.Url.CreateUrlRequest request,
        io.grpc.stub.StreamObserver<url.Url.CreateUrlResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateUrlMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getUrlInfo(url.Url.UrlInfoRequest request,
        io.grpc.stub.StreamObserver<url.Url.UrlInfoResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetUrlInfoMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class UrlServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<UrlServiceBlockingStub> {
    private UrlServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UrlServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UrlServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public url.Url.UrlResponse getAllUrl(url.Url.UrlRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAllUrlMethod(), getCallOptions(), request);
    }

    /**
     */
    public url.Url.CreateUrlResponse createUrl(url.Url.CreateUrlRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateUrlMethod(), getCallOptions(), request);
    }

    /**
     */
    public url.Url.UrlInfoResponse getUrlInfo(url.Url.UrlInfoRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetUrlInfoMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class UrlServiceFutureStub extends io.grpc.stub.AbstractFutureStub<UrlServiceFutureStub> {
    private UrlServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UrlServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UrlServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<url.Url.UrlResponse> getAllUrl(
        url.Url.UrlRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAllUrlMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<url.Url.CreateUrlResponse> createUrl(
        url.Url.CreateUrlRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateUrlMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<url.Url.UrlInfoResponse> getUrlInfo(
        url.Url.UrlInfoRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetUrlInfoMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_ALL_URL = 0;
  private static final int METHODID_CREATE_URL = 1;
  private static final int METHODID_GET_URL_INFO = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final UrlServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(UrlServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_ALL_URL:
          serviceImpl.getAllUrl((url.Url.UrlRequest) request,
              (io.grpc.stub.StreamObserver<url.Url.UrlResponse>) responseObserver);
          break;
        case METHODID_CREATE_URL:
          serviceImpl.createUrl((url.Url.CreateUrlRequest) request,
              (io.grpc.stub.StreamObserver<url.Url.CreateUrlResponse>) responseObserver);
          break;
        case METHODID_GET_URL_INFO:
          serviceImpl.getUrlInfo((url.Url.UrlInfoRequest) request,
              (io.grpc.stub.StreamObserver<url.Url.UrlInfoResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class UrlServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    UrlServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return url.Url.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("UrlService");
    }
  }

  private static final class UrlServiceFileDescriptorSupplier
      extends UrlServiceBaseDescriptorSupplier {
    UrlServiceFileDescriptorSupplier() {}
  }

  private static final class UrlServiceMethodDescriptorSupplier
      extends UrlServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    UrlServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (UrlServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new UrlServiceFileDescriptorSupplier())
              .addMethod(getGetAllUrlMethod())
              .addMethod(getCreateUrlMethod())
              .addMethod(getGetUrlInfoMethod())
              .build();
        }
      }
    }
    return result;
  }
}
