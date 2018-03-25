package org.ros2.rcljava.node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.ros2.rcljava.Logger;
import org.ros2.rcljava.internal.message.Message;
import org.ros2.rcljava.internal.service.MessageService;
import org.ros2.rcljava.node.parameter.ParameterVariant;
import org.ros2.rcljava.node.service.Client;
import org.ros2.rcljava.node.service.RMWRequestId;
import org.ros2.rcljava.node.service.ServiceCallback;
import org.ros2.rcljava.node.topic.Publisher;
import org.ros2.rcljava.node.topic.Subscription;
import org.ros2.rcljava.node.topic.SubscriptionCallback;
import org.ros2.rcljava.qos.QoSProfile;
import org.ros2.rcljava.time.WallTimer;
import org.ros2.rcljava.time.WallTimerCallback;

import builtin_interfaces.msg.Time;
import rcl_interfaces.msg.ListParametersResult;
import rcl_interfaces.msg.Parameter;
import rcl_interfaces.msg.ParameterDescriptor;
import rcl_interfaces.msg.ParameterEvent;
import rcl_interfaces.msg.ParameterType;
import rcl_interfaces.msg.SetParametersResult;

public class MockNode implements Node {

    /**
     *  List of parameters
     */
    private HashMap<String, ParameterVariant<?>> parameters;

    public MockNode() {
        this.parameters = new HashMap<>();
    }


    @Override
    public void dispose() {}

    @Override
    public String getName() {
        return "MockNode";
    }

    @Override
    public <T extends Message> Publisher<T> createPublisher(Class<T> message, String topic, QoSProfile qos) {
        return new Publisher<T>() {

            @Override
            public void publish(T message) {
                // TODO Auto-generated method stub

            }

            @Override
            public void dispose() {
                // TODO Auto-generated method stub

            }

            @Override
            public void doInterProcessPublish(T arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public String getGid() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public String getIntraProcessGid() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public int getQueueSize() {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public String getTopicName() {
                // TODO Auto-generated method stub
                return null;
            }
        };
    }

    @Override
    public <T extends Message> Publisher<T> createPublisher(Class<T> message, String topic) {
        return this.createPublisher(message, topic, null);
    }

    @Override
    public <T extends Message> Subscription<T> createSubscription(Class<T> message, String topic,
            SubscriptionCallback<T> callback, QoSProfile qos) {
        return new Subscription<T>() {

            @Override
            public void dispose() {
                // TODO Auto-generated method stub

            }

            @Override
            public SubscriptionCallback<T> getCallback() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public String getTopicName() {
                // TODO Auto-generated method stub
                return null;
            }
        };
    }

    @Override
    public <T extends Message> Subscription<T> createSubscription(Class<T> message, String topic,
            SubscriptionCallback<T> callback) {
        return this.createSubscription(message, topic, callback, null);
    }

    @Override
    public <T extends MessageService> Client<T> createClient(Class<T> message, String service, QoSProfile qos) {
        // TODO Auto-generated method stub
        return new Client<T>() {

            @Override
            public <U extends Message, V extends Message> Future<V> sendRequest(U request) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public void dispose() {
                // TODO Auto-generated method stub

            }

            @Override
            public Class<? extends Message> getResponseType() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public <U extends Message> void handleResponse(RMWRequestId rmwRequestId, U responseMessage) {
                // TODO Auto-generated method stub

            }
        };
    }

    @Override
    public <T extends MessageService> Client<T> createClient(Class<T> message, String service) {
        return this.createClient(message, service, null);
    }

    @Override
    public <T extends MessageService> org.ros2.rcljava.node.service.Service<T> createService(Class<T> serviceType,
            String serviceName, ServiceCallback<?, ?> callback, QoSProfile qos) {
        return null;
    }

    @Override
    public <T extends MessageService> org.ros2.rcljava.node.service.Service<T> createService(Class<T> serviceType,
            String serviceName, ServiceCallback<?, ?> callback) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<SetParametersResult> setParameters(List<ParameterVariant<?>> parameters) {
        List<SetParametersResult> results = new ArrayList<SetParametersResult>();

        for (ParameterVariant<?> parameterVariantRequest : parameters) {
            SetParametersResult result = this.setParametersAtomically(new ArrayList<ParameterVariant<?>>(Arrays.asList(parameterVariantRequest)));
            results.add(result);
        }

        return results;
    }

    @Override
    public SetParametersResult setParametersAtomically(List<ParameterVariant<?>> parameters) {
        ParameterEvent parameter_event = new ParameterEvent();

        // TODO (jacquelinekay) Check handle parameter constraints
        SetParametersResult result = new SetParametersResult();
        result.setSuccessful(true);

        if (result.getSuccessful()){
            for (ParameterVariant<?> paramVarReq : parameters) {
                Parameter parameter = paramVarReq.toParameter();

                if (!this.parameters.containsKey(paramVarReq.getName())) {
                    if (parameter.getValue().getType() != ParameterType.PARAMETER_NOT_SET) {
                        parameter_event.getNewParameters().add(parameter);
                    }
                } else {
                    if (parameter.getValue().getType() != ParameterType.PARAMETER_NOT_SET) {
                        parameter_event.getChangedParameters().add(parameter);
                    } else {
                        parameter_event.getDeletedParameters().add(parameter);
                    }
                }
                this.parameters.put(paramVarReq.getName(), paramVarReq);
            }
        }

        return result;
    }

    @Override
    public List<ParameterVariant<?>> getParameters(List<String> names) {
        List<ParameterVariant<?>>  result = new ArrayList<ParameterVariant<?>>();

        for (String name : names) {
            ParameterVariant<?> param = this.getParameter(name);
            if (param != null) {
                result.add(param);
            }
        }

        return result;
    }

    @Override
    public ParameterVariant<?> getParameter(String name) {
        ParameterVariant<?> result = null;

        if (this.parameters.containsKey(name)) {
            result = this.parameters.get(name);
        }

        return result;
    }

    @Override
    public HashMap<String, List<String>> getTopicNamesAndTypes() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public HashMap<String, List<String>> getTopicNamesAndTypes(boolean noDemangle) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public HashMap<String, List<String>> getServiceNamesAndTypes() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> getNodeNames() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int countPublishers(String topic) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int countSubscribers(String topic) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void notifyGraphChange() {
        // TODO Auto-generated method stub

    }

    @Override
    public void notifyShutdown() {
        // TODO Auto-generated method stub

    }

    @Override
    public Object getGraphEvent() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void waitForGraphChange(Object event, int timeout) {
        // TODO Auto-generated method stub

    }

    @Override
    public int countGraphUsers() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public <T extends Message> void registerParamChangeCallback(SubscriptionCallback<T> callback) {
        // TODO Auto-generated method stub

    }

    @Override
    public String getNameSpace() {
        return "/_test/";
    }

    @Override
    public List<Byte> getParametersTypes(List<String> names) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> getParametersNames() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Queue<Subscription<? extends Message>> getSubscriptions() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Queue<Publisher<? extends Message>> getPublishers() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Queue<Client<? extends MessageService>> getClients() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Queue<org.ros2.rcljava.node.service.Service<? extends MessageService>> getServices() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Logger getLogger() {
        return new Logger(this);
    }

    @Override
    public Time getCurrentTime() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public WallTimer createWallTimer(long arg0, TimeUnit arg1, WallTimerCallback arg2) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Queue<WallTimer> getWallTimers() {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public <T extends Message> Publisher<T> createPublisher(Class<T> arg0, String arg1, int arg2) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public <T extends Message> Subscription<T> createSubscription(Class<T> arg0, String arg1,
            SubscriptionCallback<T> arg2, int arg3) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public <T extends Message> Subscription<T> createSubscription(Class<T> arg0, String arg1,
            SubscriptionCallback<T> arg2, QoSProfile arg3, boolean arg4) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public <T extends Message> Subscription<T> createSubscription(Class<T> arg0, String arg1,
            SubscriptionCallback<T> arg2, int arg3, boolean arg4) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public List<ParameterDescriptor> describeParameters(List<String> arg0) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public boolean getParameter(String arg0, ParameterVariant<?> arg1) {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public boolean getParameterOr(String arg0, ParameterVariant<?> arg1, ParameterVariant<?> arg2) {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public List<Class<?>> getParameterTypes(List<String> arg0) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public ListParametersResult listParameters(List<String> arg0, int arg1) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public <T> void setParameterIfNotSet(String arg0, T arg1) {
        // TODO Auto-generated method stub

    }


    @Override
    public void close() throws Exception {
        // TODO Auto-generated method stub

    }


    @Override
    public String getLoggerName() {
        // TODO Auto-generated method stub
        return null;
    }

}
