document.addEventListener("DOMContentLoaded", function() {
	detailPageLoader.loadDisplayInfo();
});

const detailPageLoader = {
	loadDisplayInfo : function() {
		let displayInfoId = extractPathVariable.getParameter("id");
		let httpRequest = new XMLHttpRequest();

		httpRequest.onreadystatechange = function() {
			if (httpRequest.readyState === XMLHttpRequest.DONE && httpRequest.status === 200) {
				let displayInfoDetail = JSON.parse(httpRequest.responseText);
	
				productImageSlider.loadSlider(displayInfoDetail);
				productContentContainer.initContainer(displayInfoDetail);
				productEventContainer.initContainer(displayInfoDetail);
				productBookingButton.initButton(displayInfoId);
				productReviewContainer.initContainer(displayInfoDetail);
				tabContainer.initTabContainer(displayInfoDetail);
			} else {
				if (httpRequest.readyState === XMLHttpRequest.DONE) {
					alert('요청을 처리할 수 없습니다');
					window.location.href='/naverbooking';
				}
			}
		}
		
		httpRequest.open("GET", "/naverbooking/api/products/"  + displayInfoId);
		httpRequest.send();
	}
}

const productImageSlider = {
	loadSlider : function(displayInfoDetail) {
		this.initImageSlider(displayInfoDetail);
		this.loadProductImage(displayInfoDetail);
	},
	
	loadProductImage : function(displayInfoDetail) {
		let slider = document.querySelector("#product-image-slider");
		let imageTemplate = document.querySelector("#product-image-item-template").innerHTML;

		let bindTemplate = Handlebars.compile(imageTemplate);
		
		let imageData = {
			"productDescription" : displayInfoDetail.displayInfo.productDescription,
			"productImages": displayInfoDetail.productImages
		}
		slider.innerHTML = bindTemplate(imageData);
		
		if (displayInfoDetail.productImages.length > 1) {
			let firstImage = slider.children[0].cloneNode(true);
			let secondImage = slider.children[1].cloneNode(true);
			slider.appendChild(firstImage);
			slider.insertBefore(secondImage, slider.firstChild);
		}
	},
	
	initImageSlider : function(displayInfoDetail) {
		let prevButton = document.querySelector("#prev-button");
		let nextButton = document.querySelector("#next-button");
		
		prevButton.style.display = "none";
		nextButton.style.display = "none";
		
		let firstPage = document.querySelector("#current-page");
		let totalPage = document.querySelector("#total-page");
		firstPage.innerText = 1;
		totalPage.innerText = displayInfoDetail.productImages.length;
		
		if (displayInfoDetail.productImages.length > 1) {
			prevButton.style.display = "";
			nextButton.style.display = "";
			this.changePage(displayInfoDetail);
		}
		
	},
	
	changePage : function(displayInfoDetail) {
		let slider = document.querySelector("#product-image-slider");
		let firstPage = document.querySelector("#current-page");
		let totalPage = document.querySelector("#total-page");

		const WIDTH = slider.offsetWidth;
		const LAST_DUMMY_INDEX = 3
		const FIRST_DUMMY_INDEX = 0
		const LAST_DATA_INDEX = 2
		const FIRST_DATA_INDEX = 1

		let currentIndex = FIRST_DATA_INDEX;
		slider.style.transform = "translate(-" + WIDTH * currentIndex + "px, 0px)";
		
		firstPage.innerText = currentIndex;
		totalPage.innerText = displayInfoDetail.productImages.length;
		
		document.querySelectorAll(".move_image_button").forEach(function(item) {
			item.addEventListener("click", function() {
				if (item.classList.contains("prev")) {
					currentIndex -= 1;
				} else {
					currentIndex += 1;
				}
				
				if (currentIndex === FIRST_DUMMY_INDEX) {
					slider.style.transition = "0.3s";
					slider.style.transform = "translate(-" + WIDTH * currentIndex + "px, 0px)";
					firstPage.innerText = LAST_DATA_INDEX;
					
					setTimeout(function(){
						currentIndex = LAST_DATA_INDEX;
						slider.style.transition = "0s";
						slider.style.transform = "translate(-" + WIDTH * currentIndex + "px, 0px)";
            		},300)

				} else if (currentIndex === LAST_DUMMY_INDEX) {
					slider.style.transition = "0.3s";
					slider.style.transform = "translate(-" + WIDTH * currentIndex + "px, 0px)";
					firstPage.innerText = FIRST_DATA_INDEX;
					
					setTimeout(function(){
						currentIndex = FIRST_DATA_INDEX;
						slider.style.transition = "0s";
						slider.style.transform = "translate(-" + WIDTH * currentIndex + "px, 0px)";
            		},300)
				} else {
					slider.style.transition = "0.3s";
					slider.style.transform = "translate(-" + WIDTH * currentIndex + "px, 0px)";
					firstPage.innerText = currentIndex;
				}
			})
		})
	}, 	
}

const productContentContainer = {
	initContainer : function(displayInfoDetail) {
		this.loadContent(displayInfoDetail);
		this.initCollapseButton();
	},
	
	loadContent : function(displayInfoDetail) {
		$("#product-content-box").html(displayInfoDetail.displayInfo.productContent);
		const MAX_CHARACTER = 95;
		
		if (displayInfoDetail.displayInfo.productContent.length < MAX_CHARACTER) {
			$("#content-open-button").css("display", "none");
		}
	},
	
	initCollapseButton : function() {
		$("#content-open-button").on("click", function(){
			$("#content-open-button").css("display", "none");
			$("#content-close-button").css("display", "block");
			$(".store_details").removeClass("close3");
			
		});
		
		$("#content-close-button").on("click", function(){
			$("#content-open-button").css("display", "block");
			$("#content-close-button").css("display", "none");
			$(".store_details").addClass("close3");
		});
	}
}

const productEventContainer = {
	initContainer : function(displayInfoDetail) {
		this.loadEvent(displayInfoDetail);
	},
	
	loadEvent(displayInfoDetail) {
		let eventBox = document.querySelector("#event-info-box");
		let eventList = displayInfoDetail.productPrices.filter(this.doingEvent);
		eventBox.innerHTML = "";
		
		if (eventList.length > 0) {
			eventList.forEach(function(productEvent) {
				eventBox.innerHTML += (productEvent.priceTypeName + "석 " + productEvent.discountRate + "% ");
			});
		} else {
			let eventSection = document.querySelector(".section_event");
			eventSection.innerHTML = "";
		}
	}, 
	
	doingEvent : function(event) {
		return event.discountRate > 0;
	}
}

const productBookingButton = {
	initButton : function(displayInfoId) {
		let bookingButton = document.querySelector("#booking-button");
		bookingButton.addEventListener("click", function() {
			location.href = "/naverbooking/booking?id=" + displayInfoId;
		})
	}
}

let productReviewContainer = {
	initContainer : function(displayInfoDetail) {
		this.initScoreBox(displayInfoDetail);
		this.initReviewBox(displayInfoDetail);
		this.initShowMoreReviewButton(displayInfoDetail);
	}, 
	
	initScoreBox(displayInfoDetail) {
		let reviewStar = document.querySelector("#review-score-star");
		let reviewNumerator = document.querySelector("#review-score-numerator");
		let reviewCounter = document.querySelector("#review-count");
		const MAX_SCORE = 5
		
		reviewNumerator.innerHTML = displayInfoDetail.averageScore.toFixed(1);
		reviewStar.style.width = displayInfoDetail.averageScore / MAX_SCORE * 100 + "%";
		reviewCounter.innerHTML = displayInfoDetail.comments.length + "건";
	}, 
	
	initReviewBox(displayInfoDetail) {
		let reviewListBox = document.querySelector("#review-list-box");
		let reviewTemplate = document.querySelector("#comment-item-template").innerHTML;
		let reviewBindTemplate = Handlebars.compile(reviewTemplate);
		const MAX_REVIEW_COUNT = 3;
		
		let reviewData = {
			"displayInfo" : displayInfoDetail.displayInfo,
			"comments" : displayInfoDetail.comments.slice(0, MAX_REVIEW_COUNT),
		}
		
		Handlebars.registerHelper('floatScore', function(score) {
			return score.toFixed(1);
		});
		
		Handlebars.registerHelper('formateDate', function(date) {
			return date.split(" ")[0];
		});
		
		reviewListBox.innerHTML = reviewBindTemplate(reviewData);
	}, 
	
	initShowMoreReviewButton(displayInfoDetail) {
		let showMoreReviewButton = document.querySelector("#show-more-review-button");
		const REVIEW_MAX_COUNT = 3
		
		if (displayInfoDetail.comments.length <= REVIEW_MAX_COUNT) {
			showMoreReviewButton.classList.add("hide");
		}
		
		showMoreReviewButton.addEventListener("click", function() {
			location.href = "/naverbooking/review?id=" + extractPathVariable.getParameter("id");
		})
	}
}

const tabContainer = {
	initTabContainer: function(displayInfoDetail) {
		this.loadTabContianerInfo(displayInfoDetail);
		this.initDetailInfoTab();
		this.initWayToComeTab();
	},
	initDetailInfoTab : function() {
		let detailInfoTab = document.querySelector("#detail-info-tab");
		
		detailInfoTab.addEventListener("click", function(item) {
			let wayToComeTab = document.querySelector("#way-to-come-tab");
			wayToComeTab.classList.remove("active");
			item.currentTarget.classList.add("active");
			
			let wayToComeContainer = document.querySelector(".detail_location");
			let detailInfoContainer = document.querySelector(".detail_area_wrap");
			
			detailInfoContainer.classList.remove("hide");
			wayToComeContainer.classList.add("hide");
		})
	}, 
	
	initWayToComeTab : function() {
		let wayToComeTab = document.querySelector("#way-to-come-tab");
		
		wayToComeTab.addEventListener("click", function(item) {
			let detailInfoTab = document.querySelector("#detail-info-tab");
			detailInfoTab.classList.remove("active");
			item.currentTarget.classList.add("active");
			
			let wayToComeContainer = document.querySelector(".detail_location");
			let detailInfoContainer = document.querySelector(".detail_area_wrap");
			
			detailInfoContainer.classList.add("hide");
			wayToComeContainer.classList.remove("hide");
		})
	}, 
	
	loadTabContianerInfo : function(displayInfoDetail) {
		let detailproductDescription = document.querySelector("#detail-product-description");
		let productDescription = document.querySelector("#way-to-come-product-description");
		let placeStreet = document.querySelector("#way-to-come-place-street");
		let placeLot = document.querySelector("#way-to-come-place-lot");
		let placeName = document.querySelector("#way-to-come-place-name");
		let telephone = document.querySelector("#way-to-come-place-telephone");
		let mapImage = document.querySelector("#way-to-come-map");
		
		detailproductDescription.innerHTML = displayInfoDetail.displayInfo.productContent;
		productDescription.innerHTML = displayInfoDetail.displayInfo.productDescription;
		placeStreet.innerHTML = displayInfoDetail.displayInfo.placeStreet;
		placeLot.innerHTML = displayInfoDetail.displayInfo.placeLot;
		placeName.innerHTML = displayInfoDetail.displayInfo.placeName;
		telephone.innerHTML = displayInfoDetail.displayInfo.telephone;
		mapImage.src = "/naverbooking/" + displayInfoDetail.displayInfoImage.saveFileName;
	}
}
