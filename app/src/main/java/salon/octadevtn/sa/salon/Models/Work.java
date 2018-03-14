package salon.octadevtn.sa.salon.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Work {

    @SerializedName("word_day_hours")
    @Expose
    private List<WordDayHour> wordDayHours = null;

    public List<WordDayHour> getWordDayHours() {
        return wordDayHours;
    }

    public void setWordDayHours(List<WordDayHour> wordDayHours) {
        this.wordDayHours = wordDayHours;
    }

}
