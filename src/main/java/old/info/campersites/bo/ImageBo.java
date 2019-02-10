package old.info.campersites.bo;

import java.io.Serializable;
import java.util.Date;

public class ImageBo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private Long imageId;
    private Long stopId;
    private Long userId;
    private String originalName;
    private String thumbnailName;
    private String newName;
    private String contentType;
    private Long size;
    private Date dateCreated;
    private String url;
    private String thumbnailUrl;

	public ImageBo(Long imageId) {
		this.imageId = imageId;
	}

	public ImageBo() {
	}

	@Override
	public String toString() {
		return "Image [imageId=" + imageId + ", stopId=" + stopId + ", userId=" + userId + ", originalName=" + originalName + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((imageId == null) ? 0 : imageId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ImageBo)) {
			return false;
		}
		ImageBo other = (ImageBo) obj;
		if (imageId == null) {
			if (other.getImageId() != null) {
				return false;
			}
		} else if (!imageId.equals(other.getImageId())) {
			return false;
		}
		return true;
	}

	public Long getImageId() {
		return imageId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

	public Long getStopId() {
		return stopId;
	}

	public void setStopId(Long stopId) {
		this.stopId = stopId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public String getThumbnailName() {
		return thumbnailName;
	}

	public void setThumbnailName(String thumbnailName) {
		this.thumbnailName = thumbnailName;
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

}
