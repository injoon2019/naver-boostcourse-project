document.addEventListener("DOMContentLoaded", function() {
	reviewPageLoader.loadDisplayInfo();
});

const reviewPageLoader = {
	loadDisplayInfo : function() {
		let displayInfoId = extractPathVariable.getParameter("id");
		let httpRequest = new XMLHttpRequest();

		httpRequest.onreadystatechange = function() {
			if (httpRequest.readyState === XMLHttpRequest.DONE && httpRequest.status === 200) {
				let displayInfoDetail = JSON.parse(httpRequest.responseText);
				productReviewContainer.initContainer(displayInfoDetail);
			} else {
				if (httpRequest.readyState === XMLHttpRequest.DONE) {
					alert('요청을 처리할 수 없습니다');
					window.location.href='http://localhost:8080/naverbooking/';
					return;
				}
			}
		}

		httpRequest.open("GET", "/naverbooking/api/products/"  + displayInfoId);
		httpRequest.send();
	}
}

const productReviewContainer = {
	initContainer : function(displayInfoDetail) {
		this.initScoreBox(displayInfoDetail);
		this.initReviewBox(displayInfoDetail);
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

		let reviewData = {
			"displayInfo" : displayInfoDetail.displayInfo,
			"comments" : displayInfoDetail.comments
		}

		Handlebars.registerHelper('saveFileName', function(comments) {
			return comments.commentImages.shift().saveFileName;
		});

		Handlebars.registerHelper('floatScore', function(score) {
			return score.toFixed(1);
		});

		Handlebars.registerHelper('formateDate', function(date) {
			return date.split(" ")[0];
		});
		reviewListBox.innerHTML = reviewBindTemplate(reviewData);
	}
}

