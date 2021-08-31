document.addEventListener("DOMContentLoaded", function() {
    let pageLoader = new PageLoader();
    pageLoader.loadDisplayInfo();
});

function PageLoader() {
}

PageLoader.prototype.loadDisplayInfo = function() {
    let displayInfoId = extractPathVariable.getParameter("id");
    let httpRequest = new XMLHttpRequest();

    httpRequest.onreadystatechange = function() {
        if (httpRequest.readyState === XMLHttpRequest.DONE && httpRequest.status === 200) {
            let displayInfoDetail = JSON.parse(httpRequest.responseText);

            let productContainerLoader = new ProductContainerLoader();
            let ticketCountInitializer = new TicketCounterInitializer();
            let agreementContainerInitializer = new AgreementContainerInitializer();
            let submitButtonInitializer = new SubmitButtonInitializer();

            productContainerLoader.loadProductInfo(displayInfoDetail);
            ticketCountInitializer.initTicketCounter(displayInfoDetail);
            ticketCountInitializer.initPlusMinusButtons();
            ticketCountInitializer.changeInfoByCounters();
            agreementContainerInitializer.initAgreementButtons();
            submitButtonInitializer.initSubmitButton(displayInfoDetail);
        } else if (httpRequest.readyState === XMLHttpRequest.DONE) {
            alert('요청을 처리할 수 없습니다');
            window.location.href='/naverbooking';
        }
    }

    httpRequest.open("GET", "/naverbooking/api/products/"  + displayInfoId);
    httpRequest.send();
}

function ProductContainerLoader() {
}

ProductContainerLoader.prototype.loadProductInfo = function(displayInfoDetail) {
    let productTitle = document.querySelector("#product-title");
    let productImage = document.querySelector("#product-image");
    let productPlace = document.querySelector("#product-place");
    let productOpeningHours = document.querySelector("#product-opening-hours");
    let productPrices = document.querySelector("#product-prices");

    productImage.src = "/naverbooking/" + displayInfoDetail.productImages[0].saveFileName;
    productTitle.textContent = displayInfoDetail.displayInfo.productDescription;
    productPlace.textContent = displayInfoDetail.displayInfo.placeName;
    productOpeningHours.textContent = displayInfoDetail.displayInfo.openingHours;

    productPrices.innerHTML = "";
    displayInfoDetail.productPrices.forEach(function(productPrice) {
        productPrices.innerHTML += productPrice.priceTypeName + " " + productPrice.price + "원\n";
    })
}

function TicketCounterInitializer() {
}

TicketCounterInitializer.prototype = {
    initTicketCounter : function(displayInfoDetail) {
        let ticketCounterTemplate = document.querySelector("#ticket-counter-template").innerHTML;
        let ticketContainer = document.querySelector("#ticket-container");

        let bindTemplate = Handlebars.compile(ticketCounterTemplate);
        let priceData = {
            "productPrices" : displayInfoDetail.productPrices
        }

        ticketContainer.innerHTML = bindTemplate(priceData);
    },

    initPlusMinusButtons : function () {
        let plusMinusBoxes = document.querySelectorAll(".clearfix");

        plusMinusBoxes.forEach(function(item) {
            item.addEventListener("click", function(item) {
                if (item.target.classList.contains("minus_button")) {
                    TicketCounterInitializer.prototype.respondMinusButton(item);
                } else if (item.target.classList.contains("plus_button")) {
                    TicketCounterInitializer.prototype.respondPlusButton(item);
                }
                TicketCounterInitializer.prototype.changeInfoByCounters();
            })
        })
    },

    respondMinusButton : function(item) {
        const COUNT_MIN = 0;
        let countBox = item.target.nextSibling.nextSibling;
        let currentCount = countBox.getAttribute("value");

        if (currentCount > COUNT_MIN) {
            countBox.setAttribute("value", parseInt(currentCount) - 1);
            item.target.classList.remove("disabled");
            countBox.classList.remove("disabled");
        } else {
            item.target.classList.add("disabled");
            countBox.classList.add("disabled");
        }
    },

    respondPlusButton : function(item) {
        const COUNT_MAX = 1000;
        let countBox = item.target.previousSibling.previousSibling;
        let currentCount = countBox.getAttribute("value");

        if (currentCount < COUNT_MAX) {
            countBox.setAttribute("value", parseInt(currentCount) + 1);
            item.target.classList.remove("disabled");
            countBox.classList.remove("disabled");
        } else {
            item.target.classList.add("disabled");
            countBox.classList.add("disabled");
        }
    },

    changeInfoByCounters : function() {
        let countBoxes = document.querySelectorAll(".qty");
        let totalCountNumber = 0;

        countBoxes.forEach(function(countBox) {
            let quantityBox = countBox.getElementsByClassName("count_control_input")[0];

            let currentCountNumber = parseInt(quantityBox.getAttribute("value"));
            let priceNumber = parseInt(countBox.getElementsByClassName("price")[0].innerHTML);

            totalCountNumber += currentCountNumber;

            let totalPrice = countBox.getElementsByClassName("total_price")[0];
            totalPrice.innerHTML = currentCountNumber * priceNumber;
        })

        let totalCount = document.querySelector("#totalCount");
        totalCount.innerHTML = totalCountNumber;
    }
}

function InputValidator() {
}

InputValidator.prototype = {
    validateInputs : function() {
        let isCountValid = InputValidator.prototype.validateCount();
        let isNameValid = InputValidator.prototype.validateName();
        let isPhoneNumberValid = InputValidator.prototype.validatePhoneNumber();
        let isEmailValid = InputValidator.prototype.validateEmail();

        return isCountValid && isNameValid && isPhoneNumberValid && isEmailValid;
    },

    validateCount : function() {
        let totalCountNumber = parseInt(document.querySelector("#totalCount").textContent);
        if (totalCountNumber === 0) {
            return false;
        }
        return true;
    },

    validateName : function() {
        const NAME_REGULAR_EXPRESSION = /^[가-힣]{2,4}$/;
        let nameField = document.querySelector("#name");
        let name = nameField.value;
        let nameValid = (NAME_REGULAR_EXPRESSION).test(name);

        return (name && nameValid);
    },

    validatePhoneNumber : function() {
        const PHONE_REGULAR_EXPRESSION = /^\d{3}-\d{3,4}-\d{4}$/;
        let phoneField = document.querySelector("#tel");
        let phoneNumber = phoneField.value;
        let phoneNumberValid = (PHONE_REGULAR_EXPRESSION).test(phoneNumber);

        return (phoneNumber && phoneNumberValid);
    },

    validateEmail : function() {
        const EMAIL_REGULAR_EXPRESSION = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
        let emailField = document.querySelector("#email");
        let email = emailField.value;
        let emailValid = (EMAIL_REGULAR_EXPRESSION).test(email);

        return (email && emailValid);
    },

    notValidateResponse: function() {
        let validName = this.validateName();
        let validEmail = this.validateEmail();
        let validPhone = this.validatePhoneNumber();

        if (!validName) {
            let nameField = document.querySelector("#name");
            nameField.value = ""
            nameField.placeholder = "한글 2~4자 사이로 입력해주세요";
            nameField.classList.add("input_placeholder");
        }
        if (!validEmail) {
            let emailField = document.querySelector("#email");
            emailField.value = ""
            emailField.placeholder = "xxx@xxx.xxx 같은 형식입니다";
            emailField.classList.add("input_placeholder");
        }
        if (!validPhone) {
            let phoneField = document.querySelector("#tel");
            phoneField.value = ""
            phoneField.placeholder = "xxx-xxxx-xxxx 같은 형식입니다";
            phoneField.classList.add("input_placeholder");
        }
    }
}

function AgreementContainerInitializer() {
}

AgreementContainerInitializer.prototype = {
    initAgreementButtons : function() {
        AgreementContainerInitializer.prototype.activeShowMoreButtons();
        AgreementContainerInitializer.prototype.activeSubmitButton();
    },

    activeShowMoreButtons : function() {
        let agreementContainer = document.querySelector("#agreement-container");

        agreementContainer.addEventListener("click", function(event) {
            if (event.target.classList.contains("btn_agreement") || event.target.classList.contains("show_more_text") ||
                event.target.classList.contains("fn-down2")) {
                let agreement = event.target.closest(".agreement");

                if (agreement.classList.contains("open")) {
                    agreement.classList.remove("open");
                    agreement.getElementsByClassName("show_more_text")[0].innerHTML = "보기";
                    return;
                }

                agreement.classList.add("open");
                agreement.getElementsByClassName("show_more_text")[0].innerHTML = "접기";
            }
        })
    },

    activeSubmitButton : function() {
        let agreementCheck = document.querySelector('#chk3');
        let submitButton = document.querySelector('#submit-button');

        agreementCheck.addEventListener("click", function() {
            let isAllValid = InputValidator.prototype.validateInputs();

            if (agreementCheck.checked && isAllValid) {
                submitButton.classList.remove("disable");
            }
            else {
                InputValidator.prototype.notValidateResponse();
                submitButton.classList.add("disable");
            }
        })
    }
}

function SubmitButtonInitializer() {
}

SubmitButtonInitializer.prototype = {
    initSubmitButton : function(displayInfoDetail) {
        let submitButton = document.querySelector('#submit-button');
        submitButton.addEventListener("click", function() {
            let isAllValid = InputValidator.prototype.validateInputs();
            if (!isAllValid) {
                InputValidator.prototype.notValidateResponse();
                alert("입력을 다시 확인해주세요")
                return;
            }

            if (!submitButton.classList.contains("disable")) {
                let requestParamFactory = new RequestParamFactory();
                let requestParam = requestParamFactory.getRequestParam(displayInfoDetail);

                let httpRequest = new XMLHttpRequest();
                httpRequest.onreadystatechange = function() {
                    if (httpRequest.readyState === XMLHttpRequest.DONE && httpRequest.status === 200) {
                        alert("예약이 완료되었습니다");
                        window.location.href='/naverbooking';
                    } else if (httpRequest.readyState === XMLHttpRequest.DONE) {
                        alert('요청을 처리할 수 없습니다');
                    }
                };

                httpRequest.open("POST", "/naverbooking/api/reservations");
                httpRequest.setRequestHeader("Content-Type", "application/json");
                httpRequest.send(JSON.stringify(requestParam));
            }
        })
    }
}

function RequestParamFactory() {
}

RequestParamFactory.prototype = {
    getRequestParam : function(displayInfoDetail) {
        let displayInfoId = displayInfoDetail.displayInfo.displayInfoId;
        let prices = this.getPrices();
        let productId = displayInfoDetail.displayInfo.productId;
        let reservationEmail = document.querySelector('#email').value;
        let reservationName = document.querySelector('#name').value;
        let reservationTelephone = document.querySelector('#tel').value;
        let reservationYearMonthDay = document.querySelector("#random-reservation-date").innerHTML;

        return new RequestParam(displayInfoId, prices, productId,
            reservationEmail, reservationName, reservationTelephone, reservationYearMonthDay);
    },

    getPrices : function() {
        let prices = [];
        let ticketCounters = document.querySelectorAll(".qty");

        ticketCounters.forEach(function(ticketCounter) {
            let count = ticketCounter.getElementsByClassName("count_control_input")[0].value;

            let productPriceId = ticketCounter.dataset.productpriceid;
            prices.push(new Price(count, productPriceId));
        });

        return prices;
    }
}

function RequestParam (displayInfoId, prices, productId, reservationEmail, reservationName,
                       reservationTelephone, reservationYearMonthDay) {
    this.displayInfoId = displayInfoId;
    this.prices = prices;
    this.productId = productId;
    this.reservationEmail = reservationEmail;
    this.reservationName = reservationName;
    this.reservationTelephone = reservationTelephone;
    this.reservationYearMonthDay = reservationYearMonthDay;
}

function Price (count, productPriceId, reservationInfoId, reservationInfoPriceId) {
    this.count = count;
    this.productPriceId = productPriceId;
    this.reservationInfoId = reservationInfoId;
    this.reservationInfoPriceId = reservationInfoPriceId;
}