
Feature:Rest&cucumber
	Background:
	Scenario:登录用户，获得鉴权
	Given 请求头不带参数
	Given 提交鉴权请求oauth,gimmeatoken
	When 请求地址 /v1/oauth2/token
	Then 校验状态码 200
	Then  打印请求内容

	Scenario:通过token ,允许用户登录
		Given 请求头不带参数
		Given 使用鉴权oauth2通过验证<json.access_token>
		When 请求地址 /v1/payments/payment/
		And  Int返回值属性匹配
	       |paymentsCount|4|






