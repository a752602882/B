Feature:Rest&cucumber
	Scenario Outline:test1
	Given 请求头不带参数
	When 请求地址 /api/f1/circuits/<circuitName>.json
	And  String返回值属性匹配
		|MRData.CircuitTable.Circuits.Location[0].country|<circuitCountry>|
	Examples:
        | circuitName | circuitCountry |
	    |        monza |           Italy  |
        |          spa |           Belgium|
        |       sepang |         Malaysia|

	Scenario Outline:test2
		Given 请求头不带参数
		When 请求地址 /api/f1/2015/<raceNumber>/drivers/max_verstappen/pitstops.json
		And  返回值属性包含
			|MRData.RaceTable.Races[0].PitStops|<numberOfPitstops>|
		Examples:
			| raceNumber | numberOfPitstops |
			|         1|           1|
			|         2|           3|
			|         3|           2|
			|         4|           2|