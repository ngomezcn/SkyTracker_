var u = null
var testObj = { tle1: "1 43556U 18046C   22347.51471233  .00039219  00000+0  72910-3 0  9991", tle2: "2 43556  51.6347  25.9769 0007846 338.0665  21.9986 15.48472536246962"}
var jsonStr = JSON.stringify(testObj)

const list = document.getElementById("sat_list");
list.style.maxHeight = (window.innerHeight-72-66)+'px';