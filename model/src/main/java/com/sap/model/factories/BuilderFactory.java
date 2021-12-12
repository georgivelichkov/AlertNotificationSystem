package com.sap.model.factories;

import com.sap.model.builders.*;

public class BuilderFactory<T> {

    public static ActionsBuilder action() {
        return new ActionsBuilder();
    }

    public static ConditionsBuilder conditions() {
        return new ConditionsBuilder();
    }

    public static SubscriptionsBuilder subscriptions() {
        return new SubscriptionsBuilder();
    }

    public static ResponseMetaDataBuilder metaData() { return new ResponseMetaDataBuilder(); }

}
