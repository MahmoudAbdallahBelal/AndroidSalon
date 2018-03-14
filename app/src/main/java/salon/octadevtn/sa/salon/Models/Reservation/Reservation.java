package salon.octadevtn.sa.salon.Models.Reservation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import salon.octadevtn.sa.salon.Models.Error;

public class Reservation {

    @SerializedName("error")
    @Expose
    private Error error;
    @SerializedName("tasks")
    @Expose
    private List<Task> tasks = null;

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

}
