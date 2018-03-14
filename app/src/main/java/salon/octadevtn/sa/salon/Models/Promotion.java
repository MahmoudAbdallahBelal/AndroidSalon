package salon.octadevtn.sa.salon.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Promotion {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("id_salon")
    @Expose
    private String idSalon;
    @SerializedName("description")
    @Expose
    private String description;
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
    @SerializedName("share")
    @Expose
    private String share;
    @SerializedName("id_service")
    @Expose
    private String idService;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("isFavorite")
    @Expose
    private String isFavorite;
    @SerializedName("isLike")
    @Expose
    private String isLike;
    @SerializedName("countFavorit")
    @Expose
    private Integer countFavorit;
    @SerializedName("countLike")
    @Expose
    private Integer countLike;
    @SerializedName("countComment")
    @Expose
    private Integer countComment;
    @SerializedName("salon")
    @Expose
    private Salon salon;
    @SerializedName("isComment")
    @Expose
    private String isComment;
    @SerializedName("isShare")
    @Expose
    private String isShare;


    public Salon getSalon() {
        return salon;
    }

    public void setSalon(Salon salon) {
        this.salon = salon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIdSalon() {
        return idSalon;
    }

    public void setIdSalon(String idSalon) {
        this.idSalon = idSalon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
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

    public int getCountFavorit() {
        return countFavorit;
    }

    public void setCountFavorit(Integer countFavorit) {
        this.countFavorit = countFavorit;
    }

    public Integer getCountLike() {
        return countLike;
    }

    public void setCountLike(Integer countLike) {
        this.countLike = countLike;
    }

    public Integer getCountComment() {
        return countComment;
    }

    public void setCountComment(Integer countComment) {
        this.countComment = countComment;
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
