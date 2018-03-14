package salon.octadevtn.sa.salon.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Promotiondetail {

    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("comment")
    @Expose
    private List<Comment> comment = null;
    @SerializedName("promotion")
    @Expose
    private Promotion promotion;
    @SerializedName("isFavorite")
    @Expose
    private String isFavorite;
    @SerializedName("salon")
    @Expose
    private Salon salon;
    @SerializedName("isLike")
    @Expose
    private String isLike;
    @SerializedName("isComment")
    @Expose
    private String isComment;
    @SerializedName("isShare")
    @Expose
    private String isShare;
    @SerializedName("Comment_count")
    @Expose
    private Integer commentCount;
    @SerializedName("Like_count")
    @Expose
    private Integer likeCount;
    @SerializedName("Favorite_count")
    @Expose
    private Integer favoriteCount;

    public Salon getSalon() {
        return salon;
    }

    public void setSalon(Salon salon) {
        this.salon = salon;
    }

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

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
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

    public Boolean getIsShare() {
        return isShare.equals("true");
    }

    public void setIsShare(String iss) {
        this.isShare = iss;
    }

    public Boolean getIsComment() {
        return isComment.equals("true");
    }

    public void setIsComment(String isc) {
        this.isComment = isc;
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
