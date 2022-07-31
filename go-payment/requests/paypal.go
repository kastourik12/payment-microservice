package requests

type Paypal struct {
	Total    string `json:"total"`
	Currency string `json:"currency"`
}
