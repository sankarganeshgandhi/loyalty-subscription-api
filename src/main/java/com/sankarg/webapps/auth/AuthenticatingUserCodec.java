package com.sankarg.webapps.auth;

import com.mongodb.MongoClient;
import com.sankarg.webapps.auth.model.AuthenticatingUser;
import com.sankarg.webapps.user.model.Status;
import com.sankarg.webapps.user.model.User;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class AuthenticatingUserCodec implements Codec<AuthenticatingUser> {
    @Override
    public Class<AuthenticatingUser> getEncoderClass() {
        return AuthenticatingUser.class;
    }

    @Override
    public void encode(BsonWriter bsonWriter, AuthenticatingUser user, EncoderContext encoderContext) {
        Document document = new Document();
        document.append(AuthenticatingUser.FIELD_LOGINID, user.getLoginId());
        document.append(AuthenticatingUser.FIELD_PASSWORD, user.getPassword());
        document.append(User.FIELD_STATUS, user.getStatus());
        bsonWriter.writeString(document.toJson());
        MongoClient.getDefaultCodecRegistry().get(Document.class).encode(bsonWriter, document, encoderContext);
    }

    @Override
    public AuthenticatingUser decode(BsonReader bsonReader, DecoderContext decoderContext) {
        Document document = MongoClient.getDefaultCodecRegistry().get(Document.class)
            .decode(bsonReader, decoderContext);
        User user = new User(document.getString(User.FIELD_ID), document.getString(User.FIELD_USERNAME),
                Status.valueOf(document.getString(User.FIELD_STATUS)));
        AuthenticatingUser authUser = new AuthenticatingUser(user, document.getString(AuthenticatingUser.FIELD_LOGINID),
            null);
        return authUser;
    }
}