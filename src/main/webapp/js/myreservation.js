document.addEventListener("DOMContentLoaded", function() {
    let myReservationInitializer = new MyReservationInitializer();
    myReservationInitializer.loadData();
});

function MyReservationInitializer() {
}

MyReservationInitializer.prototype = {
    loadData : function() {
        let userEmailBox = document.querySelector("#user-email");
        let userEmail = userEmailBox.innerHTML
        let httpRequest = new XMLHttpRequest();

        httpRequest.onreadystatechange = function () {
            if (httpRequest.readyState === XMLHttpRequest.DONE && httpRequest.status === 200) {
                let reservationDetail = JSON.parse(httpRequest.responseText);

                let reservationSummaryContainer = new ReservationSummaryContainer();
                let reservationCardsContainer = new ReservationCardsContainer();

                reservationCardsContainer.updateReservationCardsContainer(reservationDetail.reservations);
                reservationCardsContainer.initCancelButton(reservationDetail.reservations);
                reservationCardsContainer.initReviewButton(reservationDetail.reservations);
                reservationSummaryContainer.updateSummaryContainer(reservationDetail.reservations);
            } else if (httpRequest.readyState === XMLHttpRequest.DONE) {
                alert('요청을 처리할 수 없습니다');
            }
        }
        httpRequest.open("GET", "/naverbooking/api/reservations?reservationEmail=" + userEmail);
        httpRequest.send();
    }
}

function ReservationSummaryContainer() {
}

ReservationSummaryContainer.prototype = {
    updateSummaryContainer : function() {
        let totalCount = 0;
        totalCount += this.updatePlanCount();
        totalCount += this.updateUsedCount();
        totalCount += this.updateCancelCount();
        this.updateTotalCount(totalCount);
    },

    updateTotalCount : function(totalCount) {
        let totalCounter = document.querySelector("#total-counter");
        totalCounter.innerHTML = totalCount;
    },

    updatePlanCount: function() {
        let planCounter = document.querySelector("#plan-counter");
        let planCount = document.querySelectorAll(".confirmed_card").length;

        planCounter.innerHTML = planCount;
        return planCount;
    },

    updateUsedCount: function() {
        let usedCounter = document.querySelector("#used-counter");
        let usedCount = document.querySelectorAll(".used_card").length;

        usedCounter.innerHTML = usedCount;
        return usedCount;
    },

    updateCancelCount: function() {
        let canceledCounter = document.querySelector("#canceled-counter");
        let canceledCount = document.querySelectorAll(".canceled_card").length;

        canceledCounter.innerHTML = canceledCount;
        return canceledCount;
    }
}

function ReservationCardsContainer() {
}

ReservationCardsContainer.prototype = {
    updateReservationCardsContainer : function (reservations) {
        $('.card_item').remove();
        let confirmedCardTemplate = document.querySelector("#confirmed-card-template").innerHTML;
        let usedCardTemplate = document.querySelector("#used-card-template").innerHTML;
        let canceledCardTemplate = document.querySelector("#canceled-card-template").innerHTML;

        let currentDate = new Date();

        let confirmedBindTemplate = Handlebars.compile(confirmedCardTemplate)
        let usedBindTemplate = Handlebars.compile(usedCardTemplate)
        let canceledBindTemplate = Handlebars.compile(canceledCardTemplate)

        Handlebars.registerHelper("dateFormat", function(date) {
            return date.split(" ")[0];
        })

        for (let i = 0; i < reservations.length; i++) {
            if (reservations[i].cancelYn === false) {
                let reservationDate = new Date(reservations[i].reservationDate);

                if (currentDate <= reservationDate) {
                    this.addConfirmCard(reservations[i], confirmedBindTemplate);
                    continue;
                }
                this.addUsedCard(reservations[i], usedBindTemplate);
            } else {
                this.addCanceledCard(reservations[i], canceledBindTemplate);
            }
        }
    },

    addConfirmCard : function(reservation, bindTemplate) {
        let confirmCardBox = document.querySelector("#confirmed-card-box");
        confirmCardBox.innerHTML += bindTemplate(reservation);
    },

    addUsedCard : function(reservation, bindTemplate) {
        let usedCardBox = document.querySelector("#used-card-box");
        usedCardBox.innerHTML += bindTemplate(reservation);
    },

    addCanceledCard : function(reservation, bindTemplate) {
        let canceledCardBox = document.querySelector("#canceled-card-box");
        canceledCardBox.innerHTML += bindTemplate(reservation);
    },

    initCancelButton : function(reservations) {
        let confirmCards = document.querySelectorAll(".booking_cancel_button");

        confirmCards.forEach(function(item) {
            item.addEventListener("click", function(event) {
                let reservationId = item.closest('.card_detail').getElementsByClassName('booking_number')[0].innerHTML.split(".")[1];
                ReservationCardsContainer.prototype.loadCancelPopupInfo(item);
                ReservationCardsContainer.prototype.showCancelPopup();

                let cancelYesButton = document.querySelector('#cancel_yes');
                let cancelNoButton = document.querySelector('#cancel_no');
                let closeCancelPopupButton = document.querySelector('#close_cancel');

                cancelYesButton.addEventListener("click", function () {
                    ReservationCardsContainer.prototype.closeCancelPopup();
                    ReservationCardsContainer.prototype.submitCancel(reservationId);
                    MyReservationInitializer.prototype.loadData();
                });

                cancelNoButton.addEventListener("click", function () {
                    ReservationCardsContainer.prototype.closeCancelPopup();
                });

                closeCancelPopupButton.addEventListener("click", function () {
                    ReservationCardsContainer.prototype.closeCancelPopup();
                });
            })
        })
    },

    loadCancelPopupInfo : function (item) {
        let cancelTitle = document.querySelector('#cancel-title')
        let cancelDate = document.querySelector('#cancel-date')

        let cancelTitleText = item.closest(".card_detail").getElementsByClassName("tit")[0].innerText;
        let cancelDateText = item.closest(".card_detail").getElementsByClassName("item_dsc")[0].innerText;

        cancelTitle.innerHTML = cancelTitleText;
        cancelDate.innerHTML = cancelDateText;
    },

    showCancelPopup : function () {
        let cancelPopup = document.querySelector('.popup_booking_wrapper');
        cancelPopup.style.display = "block";
    },

    closeCancelPopup : function () {
        let cancelPopup = document.querySelector('.popup_booking_wrapper');
        cancelPopup.style.display = "none";
    },

    submitCancel : function(reservationId) {
        let httpRequest = new XMLHttpRequest();
        httpRequest.onreadystatechange = function () {
            if (httpRequest.readyState === XMLHttpRequest.DONE && httpRequest.status !== 200) {
                alert("요청을 처리할 수 없습니다");
            }
        }
        httpRequest.open("PUT", "/naverbooking/api/reservations/" + reservationId);
        httpRequest.send();
    },

    initReviewButton : function() {
        let reviewButtons = document.querySelectorAll(".review_button");
        reviewButtons.forEach(function(item) {
            item.addEventListener("click", function(event) {
                let reservationId = item.closest('.card_detail').getElementsByClassName('booking_number')[0].innerHTML.split(".")[1];
                let productTitle = item.closest('.card_detail').getElementsByClassName('tit')[0].innerHTML;
                location.href= "/naverbooking/reviewwrite?reservationInfoId=" + reservationId;
            })
        })
    }
}
