package salon.octadevtn.sa.salon.Utils;

import java.util.List;

/**
 * Created by hazemz.lababidi on 6/10/17.
 */

public class ResponseErrors {

    private boolean status;
    private String message;
    private List<Error> errors;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }

    public class Error {
        private String fieldname;
        private String message;

        public String getFieldname() {
            return fieldname;
        }

        public void setFieldname(String fieldname) {
            this.fieldname = fieldname;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

}
