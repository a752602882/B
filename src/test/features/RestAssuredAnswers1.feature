Feature:Rest&cucumber

	Scenario Outline:test1
	Given 请求头不带参数
	When 请求地址 <url>
	Then 校验状态码 <status>
    Examples:
	  | url | status |
	  |    /api/f1/2016/drivers.json |  200 |
	  |    /api/f1/incorrect.json |  500 |


	Scenario:test2
	 Given 请求头不带参数
	 When 请求地址 /api/f1/2016/drivers.json
	 Then 校验返回类型 application/json

	Scenario:test3
	 Given 请求头不带参数
	 When 请求地址 /api/f1/2014/1/circuits.json
	 And String返回值属性匹配
		 |MRData.CircuitTable.Circuits.circuitId[0] | albert_park|