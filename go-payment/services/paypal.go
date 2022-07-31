package services

import (
	paypalsdk "github.com/netlify/PayPal-Go-SDK"
	"kastouri/payment-api/requests"
	response2 "kastouri/payment-api/response"
	"log"
)

type Paypal struct {
	client *paypalsdk.Client
	logger *log.Logger
}

func NewPaypalService(c *paypalsdk.Client, l *log.Logger) *Paypal {
	return &Paypal{
		client: c,
		logger: l,
	}
}
func (s *Paypal) CreatePayment(request requests.Paypal) (string, error) {
	amount := paypalsdk.Amount{
		Total:    request.Total,
		Currency: request.Currency,
	}
	payment, err := s.client.CreateDirectPaypalPayment(amount, "http://localhost:8080/api/v1/payment/execute", "http://exemple.com/cancel", "")
	if err != nil {
		return "", err
	}
	var successLink string
	for _, link := range payment.Links {
		if link.Rel == "approval_url" {
			successLink = link.Href

		}
	}
	return successLink, nil
}
func (s *Paypal) ExecutePayment(paymentID, payerID string) (error, *response2.Paypal) {
	p, err := s.client.ExecuteApprovedPayment(paymentID, payerID)
	if err != nil {
		s.logger.Println(err.Error())
		return err, nil
	}
	r := response2.Paypal{
		PayerId:   payerID,
		PaymentId: paymentID,
		Amount:    p.Transactions[0].Amount.Total,
		Currency:  p.Transactions[0].Amount.Currency,
	}
	return nil, &r
}
