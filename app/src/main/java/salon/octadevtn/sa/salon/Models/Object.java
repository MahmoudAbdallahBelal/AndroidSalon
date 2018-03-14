package salon.octadevtn.sa.salon.Models;

/**
 * Created by Marwen octadev on 7/27/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Object {

    @SerializedName("id")
    @Expose
    private Integer id;
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
    @SerializedName("categoryname")
    @Expose
    private String categoryname;
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
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("isFavrite")
    @Expose
    private String isFavrite;
    @SerializedName("countComment")
    @Expose
    private Integer countComment;
    @SerializedName("countFavorit")
    @Expose
    private Integer countFavorit;
    @SerializedName("countLike")
    @Expose
    private Integer countLike;
    @SerializedName("isLike")
    @Expose
    private String isLike;
    @SerializedName("isComment")
    @Expose
    private String isComment;
    @SerializedName("isShare")
    @Expose
    private String isShare;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("id_category")
    @Expose
    private String idCategory;

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIsFavrite() {
        return isFavrite;
    }

    public void setIsFavrite(String isFavrite) {
        this.isFavrite = isFavrite;
    }

    public Integer getCountComment() {
        return countComment;
    }

    public void setCountComment(Integer countComment) {
        this.countComment = countComment;
    }

    public Integer getCountFavorit() {
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

    public Boolean getIsLike() {
        return isLike.equals("true");
    }

    public void setIsLike(String isLike) {
        this.isLike = isLike;
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

    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
    }

}
