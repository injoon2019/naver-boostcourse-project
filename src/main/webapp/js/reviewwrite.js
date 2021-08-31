document.addEventListener("DOMContentLoaded", function() {
    let reviewWriteInitializer = new ReviewWriteInitializer();
    let fileUploadBox = new FileUploadBox();
    let submitButtonInitializer = new SubmitButtonInitializer();

    reviewWriteInitializer.initRatingBox();
    reviewWriteInitializer.initTextArea();
    fileUploadBox.initUploader();
    submitButtonInitializer.initSubmitButton();
});

function ReviewWriteInitializer() {
}

ReviewWriteInitializer.prototype = {
    initRatingBox : function() {
        let ratingBox = document.querySelector(".rating");

        ratingBox.addEventListener("click", function(event) {
            let clickedStar = event.target;
            let clickedValue = parseInt(clickedStar.value);
            let stars = clickedStar.closest(".rating").getElementsByTagName("input");
            let score = document.querySelector(".star_rank");

            ReviewWriteInitializer.prototype.removeStars(stars);
            ReviewWriteInitializer.prototype.addStars(stars, clickedValue);
            ReviewWriteInitializer.prototype.setScore(score, clickedValue);
        })
    },

    removeStars : function(stars) {
        for (let i = 0; i < stars.length; i++) {
            if (parseInt(stars[i].value)) {
                stars[i].classList.remove("checked");
            }
        }
    },

    addStars : function(stars, clickedValue) {
        for (let i = 0; i < stars.length; i++) {
            if (parseInt(stars[i].value) <= clickedValue) {
                stars[i].classList.add("checked");
            }
        }
    },

    setScore : function(score, clickedValue) {
        if (clickedValue) {
            score.innerHTML = clickedValue;
            score.classList.remove("gray_star");
        } else {
            score.innerHTML = "0";
            score.classList.add("gray_star");
        }
    },

    initTextArea : function() {
        ReviewWriteInitializer.prototype.focusTextArea();
        ReviewWriteInitializer.prototype.blurTextArea();
        ReviewWriteInitializer.prototype.countText();
    },

    focusTextArea : function() {
        let textBox = document.querySelector(".review_contents");

        textBox.addEventListener("click",function (event) {
            event.currentTarget.querySelector(".review_write_info").style.display = "none";
            event.currentTarget.querySelectorAll("span").forEach( item => {
                item.style.display = "none";
            });
            event.currentTarget.querySelector(".review_textarea").focus();
        });
    },

    blurTextArea : function() {

    },

    countText : function() {
        $("#comment-text-area").keyup(function(){
            let inputLength = $(this).val().length;
            $("#text-counter").text(inputLength);
        });
    }
}

function FileUploadBox() {
}

FileUploadBox.prototype = {
    initUploader : function () {
        let fileOpenInput = document.querySelector('#reviewImageFileOpenInput');

        fileOpenInput.addEventListener("change", (event) => {
            let file = event.target.files;
            let imageFile = event.target.files[0];

            if (!FileUploadBox.prototype.validateFile(file)) {
                alert("jpg/png 파일을 하나만 올려주세요")
                return;
            }

            FileUploadBox.prototype.loadThumbnail(imageFile);
            FileUploadBox.prototype.initCancelUpload(imageFile);
        })
    },

    validateFile : function(files) {
        if (files.length > 1) {
            return false;
        }

        let image = files[0];
        return (["image/png", "image/jpg", "image/jpeg"].indexOf(image.type) > -1);
    },

    loadThumbnail : function(imageFile) {
        let thumbnailBox = document.querySelector(".item_thumb");
        let thumbnailListBox = document.querySelector(".item");

        thumbnailBox.src = window.URL.createObjectURL(imageFile);
        thumbnailListBox.style.display = "inline-block";
    },

    initCancelUpload : function () {
        let cancelButton = document.querySelector(".cancel_upload");

        cancelButton.addEventListener("click", function (event) {
            let thumbnailBox = document.querySelector(".item_thumb");
            let thumbnailListBox = document.querySelector(".item");

            thumbnailBox.src = "";
            thumbnailListBox.style.display = "none";
        });
    }
}

function SubmitButtonInitializer() {
}

SubmitButtonInitializer.prototype = {
    initSubmitButton : function() {
        let submitButton = document.querySelector("#comment-submit-button");
        submitButton.addEventListener("click", function (event) {
            let reservationInfoId = extractPathVariable.getParameter("reservationInfoId");
            SubmitButtonInitializer.prototype.uploadComment(reservationInfoId);
        })
    },

    uploadComment : function (reservationInfoId) {
        let photo = document.getElementById("reviewImageFileOpenInput").files[0];
        let httpRequest = new XMLHttpRequest();
        let formData = new FormData();
        formData.append("imageFile", photo);

        httpRequest.onreadystatechange = function () {
            if (httpRequest.readyState === XMLHttpRequest.DONE && httpRequest.status === 200) {
                let fileId = this.responseText;
                SubmitButtonInitializer.prototype.uploadTextData(reservationInfoId, fileId);
            } else if (httpRequest.readyState === XMLHttpRequest.DONE && httpRequest.status !== 200) {
                alert("이미지를 전송하는데 실패하였습니다");
            }
        }

        httpRequest.open("POST", "/naverbooking/api/reservations/" + reservationInfoId + "/image");
        httpRequest.send(formData);
    },

    uploadTextData : function(reservationInfoId, fileId) {
        let commentParamDto = CommentParamFactory.prototype.getCommentParamDto(reservationInfoId, fileId);
        let httpRequest = new XMLHttpRequest();

        httpRequest.onreadystatechange = function () {
            if (httpRequest.readyState === XMLHttpRequest.DONE && httpRequest.status === 200) {
                alert("리뷰를 등록하였습니다");
                window.location.href='/naverbooking/myreservation';
            }else if (httpRequest.readyState === XMLHttpRequest.DONE && httpRequest.status !== 200) {
                alert("리뷰를 등록하는데 실패하였습니다");
            }
        }

        httpRequest.open("POST", "/naverbooking/api/reservations/" + reservationInfoId + "/comments");
        httpRequest.setRequestHeader("Content-Type", "application/json");
        httpRequest.send(JSON.stringify(commentParamDto));
    }
}

function CommentParamFactory() {
}

CommentParamFactory.prototype = {
    getCommentParamDto : function(reservationInfoId, fileId){
        let score = parseInt(document.querySelector(".star_rank").innerHTML);
        let comment = document.querySelector("#comment-text-area").value;
        let productId = parseInt(document.querySelector("#product-id").innerHTML);
        return new CommentParamDto(reservationInfoId, productId, score, comment, fileId);
    }
}

function CommentParamDto (reservationInfoId, productId, score, comment, fileId) {
    this.reservationInfoId = reservationInfoId;
    this.productId = productId;
    this.score = score;
    this.comment = comment;
    this.fileId = fileId;
}
