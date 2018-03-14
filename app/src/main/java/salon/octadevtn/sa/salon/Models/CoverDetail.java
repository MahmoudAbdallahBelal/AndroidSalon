package salon.octadevtn.sa.salon.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CoverDetail {

    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("comment")
    @Expose
    private List<Comment> comment = null;
    @SerializedName("salon")
    @Expose
    private Salon salon;
    @SerializedName("cover")
    @Expose
    private Cover cover;
    @SerializedName("isFavorite")
    @Expose
    private String isFavorite;
    @SerializedName("isLike")
    @Expose
    private String isLike;
    @SerializedName("Comment_count")
    @Expose
    private Integer commentCount;
    @SerializedName("Like_count")
    @Expose
    private Integer likeCount;
    @SerializedName("Favorite_count")
    @Expose
    private Integer favoriteCount;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }

    public Salon getSalon() {
        return salon;
    }

    public void setSalon(Salon salon) {
        this.salon = salon;
    }

    public Cover getCover() {
        return cover;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }

    public Boolean getIsFavorite() {
        return isFavorite.equals("true");
    }

    public void setIsFavorite(String isFavorite) {
        this.isFavorite = isFavorite;
    }

    public Boolean getIsLike() {
        return isLike.equals("true");
    }

    public void setIsLike(String isLike) {
        this.isLike = isLike;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(Integer favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

}
