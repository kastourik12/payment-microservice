package response

type Paypal struct {
	PayerId   string `json:"payerId"`
	PaymentId string `json:"paymentId"`
	Amount    string `json:"amount"`
	Currency  string `json:"currency"`
}
