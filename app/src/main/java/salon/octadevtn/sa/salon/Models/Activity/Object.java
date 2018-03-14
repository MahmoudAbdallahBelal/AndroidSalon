package salon.octadevtn.sa.salon.Models.Activity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Object {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("id_category")
    @Expose
    private String idCategory;
    @SerializedName("id_salon")
    @Expose
    private String idSalon;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("share")
    @Expose
    private String share;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("categoryname")
    @Expose
    private String categoryname;
    @SerializedName("countComment")
    @Expose
    private Integer countComment;
    @SerializedName("countlike")
    @Expose
    private Integer countlike;
    @SerializedName("countfavorite")
    @Expose
    private Integer countfavorite;
    @SerializedName("isFavorite")
    @Expose
    private String isFavorite;
    @SerializedName("isLike")
    @Expose
    private String isLike;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("posted_date")
    @Expose
    private String postedDate;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("time_service")
    @Expose
    private String timeService;
    @SerializedName("client_time")
    @Expose
    private String clientTime;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("id_service")
    @Expose
    private String idService;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("isComment")
    @Expose
    private String isComment;
    @SerializedName("isShare")
    @Expose
    private String isShare;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
    }

    public String getIdSalon() {

        return idSalon;
    }

    public void setIdSalon(String idSalon) {
        this.idSalon = idSalon;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public Integer getCountComment() {
        return countComment;
    }

    public void setCountComment(Integer countComment) {
        this.countComment = countComment;
    }

    public Integer getCountlike() {
        return countlike;
    }

    public void setCountlike(Integer countlike) {
        this.countlike = countlike;
    }

    public Integer getCountfavorite() {
        return countfavorite;
    }

    public void setCountfavorite(Integer countfavorite) {
        this.countfavorite = countfavorite;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getTimeService() {
        return timeService;
    }

    public void setTimeService(String timeService) {
        this.timeService = timeService;
    }

    public String getClientTime() {
        return clientTime;
    }

    public void setClientTime(String clientTime) {
        this.clientTime = clientTime;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIdService() {
        return idService;
    }

    public void setIdService(String idService) {
        this.idService = idService;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

}
