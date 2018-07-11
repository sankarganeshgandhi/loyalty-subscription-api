package com.sankarg.webapps.auth;

import com.mongodb.MongoClient;
import com.sankarg.webapps.auth.model.AuthenticatingUser;
import com.sankarg.webapps.auth.model.LoggedInUser;
import com.sankarg.webapps.user.model.Status;
import com.sankarg.webapps.user.model.User;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class LoggedInUserCodec implements Codec<LoggedInUser> {
    @Override
    public Class<LoggedInUser> getEncoderClass() {
        return LoggedInUser.class;
    }

    @Override
    public void encode(BsonWriter bsonWriter, LoggedInUser user, EncoderContext encoderContext) {
        Document document = new Document();
        document.append(LoggedInUser.FIELD_LOGINID, user.getLoginId());
        document.append(LoggedInUser.FIELD_SESSIONID, user.getSessionId());
        document.append(LoggedInUser.FIELD_LOGIN_TIME, user.getLoginTime());
        document.append(LoggedInUser.FIELD_LOGOUT_TIME, user.getLogoutTime());
        bsonWriter.writeString(document.toJson());
        MongoClient.getDefaultCodecRegistry().get(Document.class).encode(bsonWriter, document, encoderContext);
    }

    @Override
    public LoggedInUser decode(BsonReader bsonReader, DecoderContext decoderContext) {
        Document document = MongoClient.getDefaultCodecRegistry().get(Document.class)
            .decode(bsonReader, decoderContext);
        User user = new User(document.getString(User.FIELD_ID), document.getString(User.FIELD_USERNAME),
                Status.valueOf(document.getString(User.FIELD_STATUS)));
        AuthenticatingUser authUser = new AuthenticatingUser(user, document.getString(AuthenticatingUser
                .FIELD_LOGINID), null);
        LoggedInUser loggedUser = new LoggedInUser(authUser, document.getString(LoggedInUser.FIELD_SESSIONID));
        if (document.get(LoggedInUser.FIELD_LOGIN_TIME) instanceof Long) {
            loggedUser.setLoginTime(document.getLong(LoggedInUser.FIELD_LOGIN_TIME).longValue());
        } else if (document.get(LoggedInUser.FIELD_LOGIN_TIME) instanceof Integer) {
            loggedUser.setLoginTime(document.getInteger(LoggedInUser.FIELD_LOGIN_TIME).intValue());
        }

        if (document.get(LoggedInUser.FIELD_LOGOUT_TIME) instanceof Long) {
            loggedUser.setLogoutTime(document.getLong(LoggedInUser.FIELD_LOGOUT_TIME).longValue());
        } else if (document.get(LoggedInUser.FIELD_LOGOUT_TIME) instanceof Integer) {
            loggedUser.setLogoutTime(document.getInteger(LoggedInUser.FIELD_LOGOUT_TIME).intValue());
        }

        return loggedUser;
    }
}