package com.wlqq.huodi.http;

/**
 * @author Cai
 *         Date 12-7-10
 */

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;


public class CustomMultiPartEntity extends MultipartEntity {

    private final ProgressListener listener;

    public CustomMultiPartEntity(final ProgressListener listener) {
        super();

        this.listener = listener;

    }

    public CustomMultiPartEntity(final HttpMultipartMode mode, final ProgressListener listener) {
        super(mode);

        this.listener = listener;
    }

    public CustomMultiPartEntity(HttpMultipartMode mode, final String boundary, final Charset charset, final ProgressListener listener) {
        super(mode, boundary, charset);

        this.listener = listener;
    }

    @Override

    public void writeTo(final OutputStream outstream) throws IOException {
        super.writeTo(new CountingOutputStream(outstream, this.getContentLength(), this.listener));
    }

    public static interface ProgressListener {

        void transferred(long transferred, long total);

    }


    public static class CountingOutputStream extends FilterOutputStream {

        private final ProgressListener listener;

        private long transferred;
        private long contentLength;

        public CountingOutputStream(final OutputStream out, long contentLength, final ProgressListener listener) {
            super(out);

            this.listener = listener;
            this.transferred = 0;
            this.contentLength = contentLength;
        }

        public void write(byte[] b, int off, int len) throws IOException {
            out.write(b, off, len);

            this.transferred += len;
            this.listener.transferred(this.transferred, contentLength);
        }

        public void write(int b) throws IOException {
            out.write(b);

            this.transferred++;
            this.listener.transferred(this.transferred, contentLength);
        }

    }

}