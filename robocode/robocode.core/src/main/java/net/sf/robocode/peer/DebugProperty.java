/*******************************************************************************
 * Copyright (c) 2001-2012 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 *
 * Contributors:
 *     Pavel Savara
 *     - Initial implementation
 *******************************************************************************/
package net.sf.robocode.peer;

import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import net.sf.robocode.serialization.*;
import robocode.control.snapshot.IDebugProperty;

/**
 * @author Pavel Savara (original)
 */
public class DebugProperty implements Serializable, IXmlSerializable,
                                      IDebugProperty {

    private static final long serialVersionUID = 1L;

    public DebugProperty() {
    }

    public DebugProperty(String key, String value) {
        this.setKey(key);
        this.setValue(value);
    }
    private String key;
    private String value;

    @Override
    public void writeXml(XmlWriter writer, SerializableOptions options) throws IOException {
        writer.startElement("debug");
        {
            writer.writeAttribute("key", getKey());
            writer.writeAttribute("value", getValue());
        }
        writer.endElement();
    }

    @Override
    public XmlReader.Element readXml(XmlReader reader) {
        return reader.expect("debug", new XmlReader.Element() {
            @Override
            public IXmlSerializable read(XmlReader reader) {
                final DebugProperty snapshot = new DebugProperty();

                reader.expect("key", new XmlReader.Attribute() {
                    @Override
                    public void read(String value) {
                        snapshot.setKey(value);
                    }
                });

                reader.expect("value", new XmlReader.Attribute() {
                    @Override
                    public void read(String value) {
                        snapshot.setValue(value);
                    }
                });

                return snapshot;
            }
        });
    }

    @Override
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    static ISerializableHelper createHiddenSerializer() {
        return new SerializableHelper();
    }

    private static class SerializableHelper implements ISerializableHelper {

        @Override
        public int sizeOf(RbSerializer serializer, Object object) {
            DebugProperty obj = (DebugProperty) object;

            return RbSerializer.SIZEOF_TYPEINFO + serializer.sizeOf(obj.key) + serializer.sizeOf(obj.value);
        }

        @Override
        public void serialize(RbSerializer serializer, ByteBuffer buffer, Object object) {
            DebugProperty obj = (DebugProperty) object;

            serializer.serialize(buffer, obj.key);
            serializer.serialize(buffer, obj.value);
        }

        @Override
        public Object deserialize(RbSerializer serializer, ByteBuffer buffer) {
            String key = serializer.deserializeString(buffer);
            String value = serializer.deserializeString(buffer);

            return new DebugProperty(key, value);
        }
    }
}
