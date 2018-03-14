package salon.octadevtn.sa.salon.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ServiceDetail {

    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("categoryname")
    @Expose
    private String categoryname;
    @SerializedName("comment")
    @Expose
    private List<Comment> comment = null;
    @SerializedName("service")
    @Expose
    private Service service;
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
    @SerializedName("isComment")
    @Expose
    private String isComment;
    @SerializedName("isShare")
    @Expose
    private String isShare;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
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

    public Boolean getIsShare() {
        return isShare.equals("true");
    }

    public void setIsShare(String isShare) {
        this.isShare = isShare;
    }

    public Boolean getIsComment() {
        return isComment.equals("true");
    }

    public void setIsComment(String isComment) {
        this.isComment = isComment;
    }

}
