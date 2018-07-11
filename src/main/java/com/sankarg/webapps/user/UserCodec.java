package com.sankarg.webapps.user;

import com.sankarg.webapps.user.model.Status;
import com.sankarg.webapps.user.model.User;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class UserCodec implements Codec<User> {
    @Override
    public Class<User> getEncoderClass() {
        return User.class;
    }

    @Override
    public void encode(BsonWriter bsonWriter, User user, EncoderContext encoderContext) {
        Document document = new Document();
        document.append(User.FIELD_ID, user.getId());
        document.append(User.FIELD_USERNAME, user.getUserName());
        document.append(User.FIELD_ADDERSSES, user.getAddresses());
        document.append(User.FIELD_EMAILADDRESSES, user.getEmailAddresses());
        document.append(User.FIELD_PHONENUMBERS, user.getPhoneNumbers());
        document.append(User.FIELD_STATUS, user.getStatus());
        bsonWriter.writeString(document.toJson());
    }

    @Override
    public User decode(BsonReader bsonReader, DecoderContext decoderContext) {
        Document document = Document.parse(bsonReader.readString());
        User user = new User(document.getString(User.FIELD_ID), document.getString(User.FIELD_USERNAME),
                Status.valueOf(document.getString(User.FIELD_STATUS)));
        return user;
    }
}