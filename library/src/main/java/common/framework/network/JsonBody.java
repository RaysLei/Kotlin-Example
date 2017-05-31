package common.framework.network;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.internal.Util;
import okio.BufferedSink;

/**
 * Created by Rays on 2017/3/31.
 */
public final class JsonBody extends RequestBody {
    private static final MediaType CONTENT_TYPE =
            MediaType.parse("application/json; charset=utf-8");

    private final int byteCount;
    private final byte[] bytes;

    private JsonBody(JSONObject jsonObject) {
        if (jsonObject.length() == 0) {
            throw new NullPointerException("json body is empty");
        }
        bytes = jsonObject.toString().getBytes(Util.UTF_8);
        byteCount = bytes.length;
    }

    @Override
    public MediaType contentType() {
        return CONTENT_TYPE;
    }

    @Override
    public long contentLength() throws IOException {
        return byteCount;
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        sink.write(bytes, 0, byteCount);
    }

    public static final class Builder {
        private final JSONObject json = new JSONObject();

        public Builder add(String name, Object value) {
            try {
                json.put(name, value);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return this;
        }

        public Builder add(String name, boolean value) {
            try {
                json.put(name, value);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return this;
        }

        public Builder add(String name, double value) {
            try {
                json.put(name, value);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return this;
        }

        public Builder add(String name, long value) {
            try {
                json.put(name, value);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return this;
        }

        public Builder add(String name, int value) {
            try {
                json.put(name, value);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return this;
        }

        public JsonBody build() {
            return new JsonBody(json);
        }
    }
}
