import {
  Callout,
  Card,
  CardBody,
  CardHeader,
  Code,
  Divider,
  Grid,
  H1,
  H2,
  H3,
  Stack,
  Stat,
  Table,
  Text,
} from "cursor/canvas";

const VS_HEADERS = [
  "Variante",
  "W",
  "p",
  "Mediana da diferença",
  "Q1 da diferença",
  "Q3 da diferença",
  "Mediana da %",
  "Q1 da %",
  "Q3 da %",
  "Aumentou",
  "Diminuiu",
  "Igual"
] as string[];

const PAIR_HEADERS = [
  "Par",
  "W",
  "p (Holm)",
  "Mediana da diferença",
  "Q1 da diferença",
  "Q3 da diferença",
  "Mediana da %",
  "Q1 da %",
  "Q3 da %",
  "Aumentou",
  "Diminuiu",
  "Igual"
] as string[];

const COMP_HEADERS = [
  "Par",
  "W",
  "p",
  "Mediana da diferença",
  "Q1 da diferença",
  "Q3 da diferença",
  "Mediana da %",
  "Q1 da %",
  "Q3 da %",
  "Aumentou",
  "Diminuiu",
  "Igual"
] as string[];

const SHAPIRO_HEADERS = [
  "Métrica",
  "Variante",
  "n",
  "W",
  "p",
  "Normal (α = 0.05)",
] as string[];

const SHAPIRO = [
  ["duplicated_statements", "implicit", "22", "0.8377", "0.002071", "não"],
  ["duplicated_statements", "residual-implicit", "22", "0.7751", "0.0002090", "não"],
  ["duplicated_statements", "delegated", "22", "0.4931", "1.133e-07", "não"],
  ["duplicated_statements", "implicit+delegated", "22", "0.6928", "1.587e-05", "não"],
  ["duplicated_statements", "delegated+implicit", "22", "0.6585", "6.066e-06", "não"],
  ["duplicated_statements", "residual-implicit+delegated", "22", "0.6525", "5.158e-06", "não"],
  ["duplicated_statements", "delegated+residual-implicit", "22", "0.6503", "4.855e-06", "não"],
  ["test_classes", "implicit", "22", "0.7733", "0.0001970", "não"],
  ["test_classes", "residual-implicit", "22", "0.8562", "0.004385", "não"],
  ["test_classes", "delegated", "22", "0.2810", "2.098e-09", "não"],
  ["test_classes", "implicit+delegated", "22", "0.7733", "0.0001970", "não"],
  ["test_classes", "delegated+implicit", "22", "0.7870", "0.0003153", "não"],
  ["test_classes", "residual-implicit+delegated", "22", "0.8562", "0.004385", "não"],
  ["test_classes", "delegated+residual-implicit", "22", "0.8298", "0.001520", "não"],
  ["setup_methods", "implicit", "22", "0.9015", "0.03181", "não"],
  ["setup_methods", "residual-implicit", "22", "0.8601", "0.005156", "não"],
  ["setup_methods", "delegated", "22", "0.3333", "4.989e-09", "não"],
  ["setup_methods", "implicit+delegated", "22", "0.9015", "0.03181", "não"],
  ["setup_methods", "delegated+implicit", "22", "0.8986", "0.02785", "não"],
  ["setup_methods", "residual-implicit+delegated", "22", "0.8601", "0.005156", "não"],
  ["setup_methods", "delegated+residual-implicit", "22", "0.8404", "0.002305", "não"],
  ["attributes", "implicit", "22", "0.5368", "2.971e-07", "não"],
  ["attributes", "residual-implicit", "22", "0.8211", "0.001091", "não"],
  ["attributes", "delegated", "22", "0.3011", "2.870e-09", "não"],
  ["attributes", "implicit+delegated", "22", "0.5368", "2.971e-07", "não"],
  ["attributes", "delegated+implicit", "22", "0.4898", "1.057e-07", "não"],
  ["attributes", "residual-implicit+delegated", "22", "0.8211", "0.001091", "não"],
  ["attributes", "delegated+residual-implicit", "22", "0.8850", "0.01508", "não"],
  ["helper_methods", "implicit", "22", "0.2215", "1.353e-09", "não"],
  ["helper_methods", "residual-implicit", "22", "0.2215", "1.353e-09", "não"],
  ["helper_methods", "delegated", "22", "0.5857", "9.336e-07", "não"],
  ["helper_methods", "implicit+delegated", "22", "0.5567", "4.691e-07", "não"],
  ["helper_methods", "delegated+implicit", "22", "0.5857", "9.336e-07", "não"],
  ["helper_methods", "residual-implicit+delegated", "22", "0.5387", "3.098e-07", "não"],
  ["helper_methods", "delegated+residual-implicit", "22", "0.5857", "9.336e-07", "não"],
  ["total_statements", "implicit", "22", "0.8195", "0.001029", "não"],
  ["total_statements", "residual-implicit", "22", "0.7792", "0.0002404", "não"],
  ["total_statements", "delegated", "22", "0.4748", "7.692e-08", "não"],
  ["total_statements", "implicit+delegated", "22", "0.7172", "3.270e-05", "não"],
  ["total_statements", "delegated+implicit", "22", "0.6753", "9.658e-06", "não"],
  ["total_statements", "residual-implicit+delegated", "22", "0.6542", "5.394e-06", "não"],
  ["total_statements", "delegated+residual-implicit", "22", "0.6467", "4.412e-06", "não"],
] as string[][];

const VS_DUPLICATED = [
  [
    "implicit",
    "46.00",
    "0.02759",
    "-42.50",
    "-228.0",
    "-11.00",
    "-6.514",
    "-17.57",
    "-2.560",
    "3",
    "17",
    "2"
  ],
  [
    "residual-implicit",
    "0.000",
    "8.845e-05",
    "-94.50",
    "-582.8",
    "-35.00",
    "-11.04",
    "-20.50",
    "-5.428",
    "0",
    "20",
    "2"
  ],
  [
    "delegated",
    "0.000",
    "8.857e-05",
    "-120.0",
    "-251.3",
    "-33.25",
    "-10.73",
    "-16.55",
    "-6.320",
    "0",
    "20",
    "2"
  ],
  [
    "implicit+delegated",
    "0.000",
    "4.010e-05",
    "-170.0",
    "-714.8",
    "-34.25",
    "-17.47",
    "-23.94",
    "-14.01",
    "0",
    "22",
    "0"
  ],
  [
    "delegated+implicit",
    "3.000",
    "6.085e-05",
    "-165.5",
    "-597.0",
    "-34.25",
    "-17.38",
    "-23.81",
    "-10.13",
    "1",
    "21",
    "0"
  ],
  [
    "residual-implicit+delegated",
    "0.000",
    "4.010e-05",
    "-186.5",
    "-913.0",
    "-57.25",
    "-20.86",
    "-25.64",
    "-16.40",
    "0",
    "22",
    "0"
  ],
  [
    "delegated+residual-implicit",
    "0.000",
    "4.010e-05",
    "-188.5",
    "-728.0",
    "-55.00",
    "-19.86",
    "-25.04",
    "-14.90",
    "0",
    "22",
    "0"
  ]
] as string[][];

const VS_CLASSES = [
  [
    "implicit",
    "7.500",
    "0.0002724",
    "185.0",
    "13.25",
    "564.5",
    "188.1",
    "35.44",
    "300.1",
    "17",
    "3",
    "2"
  ],
  [
    "residual-implicit",
    "32.50",
    "0.006788",
    "13.00",
    "0.000",
    "95.25",
    "26.12",
    "0.000",
    "55.55",
    "15",
    "5",
    "2"
  ],
  [
    "delegated",
    "0.000",
    "0.1797",
    "0.000",
    "0.000",
    "0.000",
    "0.000",
    "0.000",
    "0.000",
    "0",
    "2",
    "20"
  ],
  [
    "implicit+delegated",
    "7.500",
    "0.0002724",
    "185.0",
    "13.25",
    "564.5",
    "188.1",
    "35.44",
    "300.1",
    "17",
    "3",
    "2"
  ],
  [
    "delegated+implicit",
    "7.000",
    "0.0003976",
    "184.0",
    "10.25",
    "416.0",
    "180.8",
    "29.62",
    "288.8",
    "17",
    "2",
    "3"
  ],
  [
    "residual-implicit+delegated",
    "32.50",
    "0.006788",
    "13.00",
    "0.000",
    "95.25",
    "26.12",
    "0.000",
    "55.55",
    "15",
    "5",
    "2"
  ],
  [
    "delegated+residual-implicit",
    "32.50",
    "0.006792",
    "10.50",
    "0.000",
    "93.25",
    "22.72",
    "0.000",
    "56.46",
    "15",
    "5",
    "2"
  ]
] as string[][];

const VS_SETUPS = [
  [
    "implicit",
    "42.00",
    "0.03292",
    "17.50",
    "0.000",
    "98.25",
    "226.4",
    "0.000",
    "570.7",
    "15",
    "4",
    "3"
  ],
  [
    "residual-implicit",
    "27.00",
    "0.003589",
    "20.00",
    "5.500",
    "115.8",
    "193.8",
    "9.722",
    "595.1",
    "17",
    "3",
    "2"
  ],
  [
    "delegated",
    "0.000",
    "0.1797",
    "0.000",
    "0.000",
    "0.000",
    "0.000",
    "0.000",
    "0.000",
    "0",
    "2",
    "20"
  ],
  [
    "implicit+delegated",
    "42.00",
    "0.03292",
    "17.50",
    "0.000",
    "98.25",
    "226.4",
    "0.000",
    "570.7",
    "15",
    "4",
    "3"
  ],
  [
    "delegated+implicit",
    "41.50",
    "0.03130",
    "17.50",
    "0.000",
    "96.50",
    "228.6",
    "0.000",
    "551.2",
    "15",
    "4",
    "3"
  ],
  [
    "residual-implicit+delegated",
    "27.00",
    "0.003589",
    "20.00",
    "5.500",
    "115.8",
    "193.8",
    "9.722",
    "595.1",
    "17",
    "3",
    "2"
  ],
  [
    "delegated+residual-implicit",
    "24.00",
    "0.002491",
    "17.50",
    "5.000",
    "113.5",
    "188.9",
    "6.944",
    "578.0",
    "17",
    "3",
    "2"
  ]
] as string[][];

const VS_ATTRIBUTES = [
  [
    "implicit",
    "83.00",
    "0.4114",
    "8.000",
    "-24.75",
    "68.00",
    "22.64",
    "-14.59",
    "48.67",
    "13",
    "7",
    "2"
  ],
  [
    "residual-implicit",
    "39.00",
    "0.01373",
    "48.00",
    "4.250",
    "163.3",
    "28.30",
    "2.703",
    "96.95",
    "17",
    "3",
    "2"
  ],
  [
    "delegated",
    "0.000",
    "0.1797",
    "0.000",
    "0.000",
    "0.000",
    "0.000",
    "0.000",
    "0.000",
    "0",
    "2",
    "20"
  ],
  [
    "implicit+delegated",
    "83.00",
    "0.4114",
    "8.000",
    "-24.75",
    "68.00",
    "22.64",
    "-14.59",
    "48.67",
    "13",
    "7",
    "2"
  ],
  [
    "delegated+implicit",
    "87.00",
    "0.5016",
    "6.000",
    "-24.75",
    "51.75",
    "13.27",
    "-14.59",
    "28.57",
    "13",
    "7",
    "2"
  ],
  [
    "residual-implicit+delegated",
    "39.00",
    "0.01373",
    "48.00",
    "4.250",
    "163.3",
    "28.30",
    "2.703",
    "96.95",
    "17",
    "3",
    "2"
  ],
  [
    "delegated+residual-implicit",
    "35.00",
    "0.008962",
    "38.00",
    "4.750",
    "159.8",
    "24.22",
    "4.865",
    "84.51",
    "17",
    "3",
    "2"
  ]
] as string[][];

const VS_HELPERS = [
  [
    "implicit",
    "0.000",
    "0.3173",
    "0.000",
    "0.000",
    "0.000",
    "0.000",
    "0.000",
    "0.000",
    "0",
    "1",
    "21"
  ],
  [
    "residual-implicit",
    "0.000",
    "0.3173",
    "0.000",
    "0.000",
    "0.000",
    "0.000",
    "0.000",
    "0.000",
    "0",
    "1",
    "21"
  ],
  [
    "delegated",
    "3.500",
    "0.0001507",
    "27.00",
    "7.250",
    "83.00",
    "24.76",
    "8.634",
    "66.16",
    "19",
    "1",
    "2"
  ],
  [
    "implicit+delegated",
    "10.00",
    "0.0003898",
    "19.50",
    "7.000",
    "93.25",
    "20.32",
    "10.63",
    "49.42",
    "19",
    "1",
    "2"
  ],
  [
    "delegated+implicit",
    "3.500",
    "0.0001507",
    "27.00",
    "7.250",
    "83.00",
    "24.76",
    "8.634",
    "66.16",
    "19",
    "1",
    "2"
  ],
  [
    "residual-implicit+delegated",
    "10.00",
    "0.0003893",
    "19.50",
    "7.000",
    "81.00",
    "18.94",
    "8.207",
    "44.93",
    "19",
    "1",
    "2"
  ],
  [
    "delegated+residual-implicit",
    "3.500",
    "0.0001507",
    "27.00",
    "7.250",
    "83.00",
    "24.76",
    "8.634",
    "66.16",
    "19",
    "1",
    "2"
  ]
] as string[][];

const VS_TOTAL = [
  [
    "implicit",
    "57.00",
    "0.04200",
    "-49.50",
    "-83.00",
    "-2.000",
    "-2.275",
    "-4.583",
    "-0.09796",
    "5",
    "16",
    "1"
  ],
  [
    "residual-implicit",
    "0.000",
    "8.845e-05",
    "-88.50",
    "-572.8",
    "-28.75",
    "-3.776",
    "-6.003",
    "-2.187",
    "0",
    "20",
    "2"
  ],
  [
    "delegated",
    "0.000",
    "8.857e-05",
    "-93.00",
    "-215.5",
    "-20.25",
    "-3.155",
    "-4.991",
    "-1.373",
    "0",
    "20",
    "2"
  ],
  [
    "implicit+delegated",
    "8.000",
    "0.0001195",
    "-148.5",
    "-570.3",
    "-23.75",
    "-5.855",
    "-8.707",
    "-3.390",
    "1",
    "21",
    "0"
  ],
  [
    "delegated+implicit",
    "10.00",
    "0.0001554",
    "-142.5",
    "-443.3",
    "-23.75",
    "-5.459",
    "-8.946",
    "-2.422",
    "2",
    "20",
    "0"
  ],
  [
    "residual-implicit+delegated",
    "0.000",
    "4.005e-05",
    "-159.5",
    "-760.3",
    "-43.75",
    "-6.787",
    "-10.54",
    "-5.693",
    "0",
    "22",
    "0"
  ],
  [
    "delegated+residual-implicit",
    "0.000",
    "4.005e-05",
    "-159.0",
    "-628.3",
    "-40.25",
    "-5.830",
    "-10.26",
    "-5.078",
    "0",
    "22",
    "0"
  ]
] as string[][];

const FRIEDMAN = [
  [
    "114.5",
    "7",
    "4.877e-13"
  ]
] as string[][];

const PAIRWISE = [
  [
    "implicit vs residual-implicit",
    "0.000",
    "0.002545",
    "-20.00",
    "-144.5",
    "-4.750",
    "-3.388",
    "-5.700",
    "-0.6399",
    "0",
    "18",
    "4"
  ],
  [
    "implicit vs delegated",
    "105.0",
    "0.9703",
    "-8.000",
    "-50.25",
    "24.50",
    "-1.446",
    "-8.144",
    "10.05",
    "8",
    "14",
    "0"
  ],
  [
    "implicit vs implicit+delegated",
    "0.000",
    "0.001678",
    "-100.5",
    "-220.0",
    "-28.25",
    "-10.17",
    "-11.98",
    "-5.825",
    "0",
    "20",
    "2"
  ],
  [
    "implicit vs delegated+implicit",
    "0.000",
    "0.001678",
    "-88.50",
    "-124.8",
    "-28.25",
    "-8.852",
    "-11.31",
    "-4.335",
    "0",
    "20",
    "2"
  ],
  [
    "implicit vs residual-implicit+delegated",
    "0.000",
    "0.001251",
    "-105.0",
    "-318.8",
    "-33.25",
    "-13.34",
    "-17.56",
    "-8.813",
    "0",
    "21",
    "1"
  ],
  [
    "implicit vs delegated+residual-implicit",
    "0.000",
    "0.001251",
    "-103.5",
    "-271.8",
    "-35.00",
    "-11.30",
    "-16.40",
    "-8.607",
    "0",
    "21",
    "1"
  ],
  [
    "residual-implicit vs delegated",
    "103.5",
    "0.9703",
    "-0.5000",
    "-28.50",
    "150.3",
    "-0.1106",
    "-4.909",
    "14.73",
    "10",
    "11",
    "1"
  ],
  [
    "residual-implicit vs implicit+delegated",
    "12.50",
    "0.003432",
    "-39.00",
    "-120.5",
    "-6.000",
    "-4.744",
    "-9.698",
    "-0.9800",
    "3",
    "18",
    "1"
  ],
  [
    "residual-implicit vs delegated+implicit",
    "45.50",
    "0.08979",
    "-28.50",
    "-85.50",
    "-0.5000",
    "-3.725",
    "-9.819",
    "-0.1205",
    "5",
    "16",
    "1"
  ],
  [
    "residual-implicit vs residual-implicit+delegated",
    "0.000",
    "0.001678",
    "-97.00",
    "-195.0",
    "-25.00",
    "-9.413",
    "-11.61",
    "-5.857",
    "0",
    "20",
    "2"
  ],
  [
    "residual-implicit vs delegated+residual-implicit",
    "0.000",
    "0.001678",
    "-59.00",
    "-125.3",
    "-27.75",
    "-8.517",
    "-10.86",
    "-4.633",
    "0",
    "20",
    "2"
  ],
  [
    "delegated vs implicit+delegated",
    "46.00",
    "0.1104",
    "-33.50",
    "-279.3",
    "-11.00",
    "-7.850",
    "-17.38",
    "-3.647",
    "3",
    "17",
    "2"
  ],
  [
    "delegated vs delegated+implicit",
    "47.00",
    "0.1104",
    "-26.00",
    "-161.8",
    "-9.500",
    "-5.523",
    "-15.80",
    "-0.9149",
    "3",
    "17",
    "2"
  ],
  [
    "delegated vs residual-implicit+delegated",
    "0.000",
    "0.001678",
    "-72.50",
    "-473.8",
    "-30.25",
    "-10.54",
    "-20.09",
    "-5.959",
    "0",
    "20",
    "2"
  ],
  [
    "delegated vs delegated+residual-implicit",
    "0.000",
    "0.001678",
    "-70.50",
    "-445.5",
    "-30.25",
    "-9.545",
    "-18.71",
    "-5.746",
    "0",
    "20",
    "2"
  ],
  [
    "implicit+delegated vs delegated+implicit",
    "22.50",
    "0.09298",
    "2.000",
    "0.000",
    "39.50",
    "0.3827",
    "0.000",
    "1.669",
    "13",
    "3",
    "6"
  ],
  [
    "implicit+delegated vs residual-implicit+delegated",
    "0.000",
    "0.002545",
    "-18.50",
    "-114.3",
    "-4.000",
    "-3.148",
    "-3.964",
    "-0.7093",
    "0",
    "18",
    "4"
  ],
  [
    "implicit+delegated vs delegated+residual-implicit",
    "13.00",
    "0.02115",
    "-12.00",
    "-46.75",
    "0.000",
    "-1.931",
    "-3.193",
    "0.000",
    "2",
    "15",
    "5"
  ],
  [
    "delegated+implicit vs residual-implicit+delegated",
    "8.000",
    "0.004171",
    "-17.00",
    "-169.3",
    "-3.250",
    "-3.384",
    "-6.604",
    "-0.9341",
    "1",
    "18",
    "3"
  ],
  [
    "delegated+implicit vs delegated+residual-implicit",
    "1.000",
    "0.002557",
    "-15.50",
    "-101.0",
    "-7.250",
    "-2.905",
    "-4.470",
    "-0.8829",
    "1",
    "17",
    "4"
  ],
  [
    "residual-implicit+delegated vs delegated+residual-implicit",
    "15.00",
    "0.04281",
    "2.500",
    "0.000",
    "34.25",
    "0.5621",
    "0.000",
    "1.438",
    "14",
    "2",
    "6"
  ]
] as string[][];

const MEDIANS = [
  [
    "original",
    "1162",
    "312.0",
    "3866"
  ],
  [
    "implicit",
    "948.5",
    "290.5",
    "2570"
  ],
  [
    "residual-implicit",
    "931.5",
    "279.3",
    "2451"
  ],
  [
    "delegated",
    "982.5",
    "308.5",
    "3288"
  ],
  [
    "implicit+delegated",
    "839.0",
    "282.5",
    "2410"
  ],
  [
    "delegated+implicit",
    "846.5",
    "282.3",
    "2478"
  ],
  [
    "residual-implicit+delegated",
    "818.5",
    "275.8",
    "2322"
  ],
  [
    "delegated+residual-implicit",
    "825.5",
    "275.8",
    "2392"
  ]
] as string[][];

const COMPOSITION = [
  [
    "implicit+delegated vs implicit",
    "0.000",
    "8.832e-05",
    "-100.5",
    "-220.0",
    "-28.25",
    "-10.17",
    "-11.98",
    "-5.825",
    "0",
    "20",
    "2"
  ],
  [
    "delegated+implicit vs delegated",
    "47.00",
    "0.03031",
    "-26.00",
    "-161.8",
    "-9.500",
    "-5.523",
    "-15.80",
    "-0.9149",
    "3",
    "17",
    "2"
  ],
  [
    "residual-implicit+delegated vs residual-implicit",
    "0.000",
    "8.832e-05",
    "-97.00",
    "-195.0",
    "-25.00",
    "-9.413",
    "-11.61",
    "-5.857",
    "0",
    "20",
    "2"
  ],
  [
    "delegated+residual-implicit vs delegated",
    "0.000",
    "8.845e-05",
    "-70.50",
    "-445.5",
    "-30.25",
    "-9.545",
    "-18.71",
    "-5.746",
    "0",
    "20",
    "2"
  ]
] as string[][];


function MetricTable({
  titulo,
  coluna,
  rows,
}: {
  titulo: string;
  coluna: string;
  rows: string[][];
}) {
  return (
    <Stack gap={6}>
      <Text weight="semibold">
        {titulo} (<Code>{coluna}</Code>)
      </Text>
      <Table striped stickyHeader headers={VS_HEADERS} rows={rows} />
    </Stack>
  );
}

export default function ExperimentoNDesign() {
  return (
    <Stack gap={28}>
      <Stack gap={8}>
        <H1>Experimento n: refatoração multi-projeto</H1>
        <Text tone="secondary">
          Comparar configuração implícita, residual e delegada, e as composições
          das duas primeiras com a delegada, em todos os projetos de{" "}
          <Code>external-projects/</Code> mais o Róża. Cada projeto gera uma
          linha por variante no CSV. Só o SAAS entra partido: cada projeto de
          teste é um sujeito.
        </Text>
      </Stack>

      <Grid columns={4} gap={16}>
        <Stat value="22" label="Sujeitos (17 libs + 4 SAAS + Róża)" />
        <Stat value="8" label="Linhas por sujeito (original + 7 variantes)" />
        <Stat value="176" label="Linhas no CSV" />
        <Stat value="32g" label="Heap no Gradle" />
      </Grid>

      <Callout tone="info" title="Pergunta">
        A refatoração reduz as sentenças duplicadas de configuração em relação
        ao original? Fazer uma estratégia e depois a outra reduz mais do que
        fazer só uma?
      </Callout>

      <Stack gap={10}>
        <H2>Sujeitos</H2>
        <Text>
          Bibliotecas com vários módulos de teste entram como um sujeito só: o
          loader recebe todas as pastas de teste. Só o SAAS parte.
        </Text>
        <Table
          striped
          stickyHeader
          headers={["Sujeito", "Pastas de teste", "Notas"]}
          rows={[
            ["roza", "src/test/java", "Suíte do próprio Róża"],
            ["commons-csv", "src/test/java", "Uma pasta"],
            ["commons-lang", "src/test/java", "Uma pasta"],
            ["commons-math", "*/src/test/java", "Várias pastas, um sujeito"],
            ["commons-text", "src/test/java", "Uma pasta"],
            ["java-string-similarity", "src/test/java", "Uma pasta"],
            ["javaparser", "*/src/test/java", "Sem src/test/resources"],
            ["jfreechart", "src/test/java", "Uma pasta"],
            ["joda-money", "src/test/java", "Uma pasta"],
            ["gson", "*/src/test/java", "Várias pastas, um sujeito"],
            ["java-hamcrest", "hamcrest/src/test/java", "Uma pasta"],
            ["ektorp", "*/src/test/java", "Várias pastas, um sujeito"],
            ["rest-assured", "*/src/test/java", "Só Java"],
            ["junit4", "src/test/java", "Sem src/main"],
            ["cobertura", "*/src/test/java", "cobertura, flush-war, conversion e metrics; pasta java, sem resources"],
            ["couchdb-lucene", "src/test/java", "Uma pasta"],
            ["picon", "test/", "Layout Eclipse"],
            ["selenium", "java/test", "Layout Bazel"],
            ["saas+teste", "módulo, recursivo", "Projeto de teste SAAS"],
            ["saas+teste+moodle", "módulo, recursivo", "Projeto de teste SAAS"],
            ["saas+teste+selenium", "módulo, recursivo", "Projeto de teste SAAS"],
            ["saas+teste+service", "módulo, recursivo", "Projeto de teste SAAS"],
          ]}
        />
        <Text tone="secondary" size="small">
          Fora: <Code>saas</Code> e <Code>saas+util</Code>. Se um sujeito não
          tiver nenhum teste parseável, registra e segue.
        </Text>
      </Stack>

      <Stack gap={10}>
        <H2>Loader</H2>
        <Text>
          Loader com uma ou mais pastas, recursivo, só <Code>.java</Code>. Várias
          pastas viram uma suíte só, não vários sujeitos.
        </Text>
      </Stack>

      <Stack gap={10}>
        <H2>Variantes</H2>
        <Text>
          Ignorar violações: decompor todos os testes parseados. Ligação simples,
          hierarquia completa. Para cada variante, escolher o agrupamento com a
          menor duplicação.
        </Text>
        <Table
          striped
          headers={[
            "Variante",
            "Decomposição",
            "Métrica",
            "Primeira refatoração",
            "Segunda refatoração",
          ]}
          rows={[
            ["original", "—", "—", "suíte como está", "—"],
            ["implicit", "com implícita", "LCCSS", "Implicit setup", "—"],
            [
              "residual-implicit",
              "com implícita",
              "LCCSS",
              "Residual implicit setup",
              "—",
            ],
            ["delegated", "sem implícita", "CCS (mín. 2)", "Delegated setup", "—"],
            [
              "implicit+delegated",
              "com, depois sem",
              "LCCSS, depois CCS (mín. 2)",
              "Implicit",
              "Delegated no resultado",
            ],
            [
              "delegated+implicit",
              "sem, depois com",
              "CCS (mín. 2), depois LCCSS",
              "Delegated",
              "Implicit no resultado",
            ],
            [
              "residual-implicit+delegated",
              "com, depois sem",
              "LCCSS, depois CCS (mín. 2)",
              "Residual implicit",
              "Delegated no resultado",
            ],
            [
              "delegated+residual-implicit",
              "sem, depois com",
              "CCS (mín. 2), depois LCCSS",
              "Delegated",
              "Residual implicit no resultado",
            ],
          ]}
        />
        <Callout tone="warning" title="Duas refatorações em sequência">
          A segunda começa do código já refatorado pela primeira. Mede de novo,
          agrupa de novo, escolhe de novo o melhor agrupamento.
        </Callout>
      </Stack>

      <Stack gap={10}>
        <H2>Pipeline por sujeito</H2>
        <Table
          headers={["Passo", "O que faz"]}
          rows={[
            ["Load e parse", "Ler os .java e identificar testes"],
            ["Original", "Medir a suíte sem refatorar"],
            [
              "Variante simples",
              "Agrupar, escolher o melhor corte, refatorar, medir",
            ],
            [
              "Variante composta",
              "Fazer a primeira variante e, no resultado, fazer a segunda",
            ],
          ]}
        />
        <Text tone="secondary" size="small">
          Log: porcentagem da variante atual e porcentagem do experimento
          inteiro, por exemplo <Code>[saas+teste] implicit 42% | total 18%</Code>.
        </Text>
      </Stack>

      <Divider />

      <Stack gap={10}>
        <H2>CSV</H2>
        <Text>
          Uma linha por projeto e variante. Contagens observadas da suíte: as
          porcentagens contra o original, entre pares e na composição saem de{" "}
          <Code>duplicated_statements</Code> na hora das tabelas e dos gráficos.
        </Text>
        <Table
          striped
          headers={["Coluna", "O que é"]}
          rows={[
            ["project", "Nome do sujeito"],
            ["variant", "original e as sete estratégias"],
            ["test_classes", "Quantas classes de teste"],
            ["setup_methods", "Quantos métodos de configuração"],
            ["attributes", "Quantos atributos"],
            [
              "helper_methods",
              "Quantos métodos helper no total (nas classes de teste e nas classes helper)",
            ],
            ["total_statements", "Quantas sentenças de configuração"],
            ["duplicated_statements", "Quantas dessas sentenças estão repetidas"],
          ]}
        />
        <Text tone="secondary" size="small">
          <Code>helper_methods</Code> soma métodos helper nas classes de teste e
          nas classes helper. Assim a implícita, que só move esses métodos para
          <Code>FooHelpers</Code>, quase não muda a conta; a delegada sobe porque
          cria <Code>setup1</Code>, <Code>setup2</Code>, … além dos que já existiam.
        </Text>
      </Stack>

      <Stack gap={10}>
        <H2>Gráficos</H2>
        <Text>
          Dois SVG gerados na execução. O boxplot resume a distribuição entre
          projetos, e o mapa de calor mantém cada projeto visível em uma forma
          mais compacta.
        </Text>
        <Table
          headers={["Arquivo", "Leitura"]}
          rows={[
            [
              "duplication-variation-distribution.svg",
              "Boxplot, mediana, IQR e um ponto por projeto",
            ],
            [
              "duplication-variation-heatmap.svg",
              "Projetos nas linhas, variantes nas colunas e variação nas células",
            ],
          ]}
        />
        <Text tone="secondary" size="small">
          Nos dois gráficos, a fórmula é{" "}
          <Code>(variante − original) / original × 100</Code>. Valor negativo
          significa menos sentenças duplicadas.
        </Text>
      </Stack>

      <Stack gap={10}>
        <H2>Estatística: o que cada teste devolve</H2>
        <Text>
          Shapiro–Wilk entra como diagnóstico nas 22 diferenças (variante −
          original) de cada métrica e cada variante, com α = 0,05. Amostra
          constante deixa W e p vazios. O teste publicado é o Wilcoxon pareado,
          escolhido a priori: 22 pares (um por sujeito), métricas de contagem,
          zeros e assimetria. Não se troca para t de Student, mesmo se o Shapiro
          não rejeitar. Wilcoxon contra o original roda nas
          seis colunas numéricas do CSV. Cada variante é um teste separado: 7
          p-valores por coluna. Conta-se em quantos projetos o valor aumentou,
          diminuiu ou ficou igual. A porcentagem é descritiva, não entra no
          teste:{" "}
          <Code>(variante − original) / original × 100</Code>. Q1 e Q3 são os
          quartis das 22 diferenças (e das 22 porcentagens).
        </Text>
        <Text>
          Depois compara-se o original e as sete variantes nas sentenças
          duplicadas com Friedman. Se houver diferença, Wilcoxon com Holm nos
          21 pares entre variantes. A melhor é a de menor mediana de
          sentenças duplicadas, desde que o par com Holm distinga. Nas
          composições, Wilcoxon compara a suíte depois das duas refatorações
          com a suíte depois só da primeira. A % da composição é{" "}
          <Code>(duplicadas da 2 − duplicadas da 1) / duplicadas da 1 × 100</Code>.
        </Text>
        <Callout tone="info" title="Exemplo: Shapiro e Wilcoxon em duplicated_statements">
          Para a variante implicit, há 22 diferenças (implicit − original, um por
          projeto). O Shapiro–Wilk dessas 22 diferenças devolve W e p; se p for
          menor que 0,05, a coluna Normal fica “não”. O Wilcoxon desses 22 pares
          devolve: um p-valor, a estatística W, e a mediana das 22 diferenças
          (em sentenças, não em %). Repete isso para as outras 6 variantes. Essa
          coluna sozinha: 7 Shapiro e 7 Wilcoxon. O mesmo pacote se repete nas
          outras cinco colunas.
        </Callout>
        <Table
          striped
          stickyHeader
          headers={["Procedimento", "Sobre o quê", "O que você anota"]}
          rows={[
            [
              "Shapiro–Wilk vs original",
              "As 6 colunas, nas 22 diferenças (variante − original)",
              "42 linhas (6 métricas × 7 variantes). Cada linha: n, W, p e se a amostra passa em α = 0,05. Amostra constante: W e p vazios. Não troca o teste publicado.",
            ],
            [
              "Wilcoxon vs original",
              "As 6 colunas: test_classes, setup_methods, attributes, helper_methods, total_statements, duplicated_statements",
              "7 linhas por coluna (uma por variante). Cada linha: p-valor, W, mediana, Q1 e Q3 da diferença na unidade da coluna. Não é %.",
            ],
            [
              "Aumentou / diminuiu / igual",
              "Cada métrica vs original",
              "7 trios de inteiros, um por variante. Exemplo implicit em duplicated_statements: 3 aumentaram, 17 diminuíram, 2 iguais.",
            ],
            [
              "Mediana, Q1 e Q3 da porcentagem",
              "duplicated_statements vs original: (variante − original) / original × 100",
              "7 trios em %. Se o original não tiver duplicação, a célula fica vazia.",
            ],
            [
              "Friedman",
              "duplicated_statements do original e das 7 variantes nos 22 projetos",
              "1 p-valor e 1 χ². Só diz se os oito não são todos iguais. Não nomeia o melhor.",
            ],
            [
              "Wilcoxon entre pares, com Holm",
              "duplicated_statements, só se o Friedman der p pequeno",
              "21 p-valores ajustados (7×6/2). Só entre variantes. Contra o original fica na tabela anterior.",
            ],
            [
              "Mediana, Q1 e Q3 da % em cada par",
              "Mesmos pares: (duplicadas de B − duplicadas de A) / duplicadas de A × 100",
              "21 trios em %. A é a base do par. Se A não tiver duplicação, a célula fica vazia.",
            ],
            [
              "Qual é a melhor",
              "Mediana de duplicated_statements nos 22 projetos, original e 7 variantes",
              "A de menor mediana. Se dois não diferirem no par com Holm, empatam.",
            ],
            [
              "Wilcoxon da composição vs a primeira sozinha",
              "Quatro pares planejados",
              "4 p-valores e 4 medianas em sentenças, com Q1 e Q3.",
            ],
          ]}
        />
        <Text>
          Resumo: Shapiro–Wilk nas seis colunas (diagnóstico). Wilcoxon contra o
          original nas seis colunas (teste publicado). Sentenças duplicadas são
          o efeito; classes, métodos de configuração, atributos, métodos helper e
          total de sentenças são o custo. Friedman e os pares com Holm ficam só
          em duplicated_statements.
        </Text>
      </Stack>

      <Divider />

      <Stack gap={16}>
        <H2>Tabelas de resultado</H2>
        <Text>
          Depois de gravar <Code>comparison.csv</Code>, o experimento calcula
          Shapiro–Wilk, Wilcoxon, Friedman, Holm, medianas, Q1, Q3 e
          aumentou/diminuiu/igual e grava um CSV por tabela em{" "}
          <Code>experiment-results/n/</Code>. Números abaixo saem desses CSV.
          Número negativo na diferença de duplicação significa queda.
        </Text>
        <Text tone="secondary" size="small">
          Fonte: <Code>experiment-results/n/*.csv</Code>, 22 sujeitos.
        </Text>

        <Stack gap={8}>
          <H3>Shapiro–Wilk das diferenças vs original</H3>
          <Text tone="secondary" size="small">
            Diagnóstico de normalidade nas 22 diferenças (variante − original),
            α = 0,05. As 42 combinações rejeitam normalidade. O teste publicado
            continua sendo o Wilcoxon. Fonte:{" "}
            <Code>shapiro-vs-original.csv</Code>.
          </Text>
          <Table striped stickyHeader headers={SHAPIRO_HEADERS} rows={SHAPIRO} />
        </Stack>

        <Stack gap={8}>
          <H3>Comparação com o original: sentenças duplicadas</H3>
          <Text tone="secondary" size="small">
            Wilcoxon pareado. Diminuiu = menos sentenças duplicadas que o
            original.
          </Text>
          <Table striped stickyHeader headers={VS_HEADERS} rows={VS_DUPLICATED} />
        </Stack>

        <Stack gap={8}>
          <H3>Comparação com o original: demais métricas</H3>
          <Text tone="secondary" size="small">
            Wilcoxon no valor da métrica. Mediana, Q1 e Q3 da diferença estão
            nas 22 diferenças (variante − original). A % usa (variante −
            original) / original × 100. Se o original for zero, a % fica vazia.
          </Text>
          <MetricTable titulo="Classes de teste" coluna="test_classes" rows={VS_CLASSES} />
          <MetricTable titulo="Métodos de configuração" coluna="setup_methods" rows={VS_SETUPS} />
          <MetricTable titulo="Atributos" coluna="attributes" rows={VS_ATTRIBUTES} />
          <MetricTable titulo="Métodos helper" coluna="helper_methods" rows={VS_HELPERS} />
          <MetricTable titulo="Sentenças de configuração" coluna="total_statements" rows={VS_TOTAL} />
        </Stack>

        <Stack gap={8}>
          <H3>Friedman nas sentenças duplicadas</H3>
          <Text tone="secondary" size="small">
            Original e as sete variantes, 22 projetos. Um teste só.
          </Text>
          <Table headers={["χ²", "gl", "p"]} rows={FRIEDMAN} />
        </Stack>

        <Stack gap={8}>
          <H3>Comparações par a par nas sentenças duplicadas</H3>
          <Text tone="secondary" size="small">
            Wilcoxon com Holm, só entre as sete variantes (21 pares). A é a
            base da porcentagem: (B − A) / A × 100. B diminuiu = menos
            duplicadas que A.
          </Text>
          <Table striped stickyHeader headers={PAIR_HEADERS} rows={PAIRWISE} />
        </Stack>

        <Stack gap={8}>
          <H3>Medianas de sentenças duplicadas</H3>
          <Text tone="secondary" size="small">
            Mediana, Q1 e Q3 das 22 contas de <Code>duplicated_statements</Code>{" "}
            de cada tratamento. Quem tem a menor mediana chegou com menos
            duplicação no projeto típico. Só declare isso se o par com Holm
            tiver p pequeno.
          </Text>
          <Table
            striped
            headers={["Tratamento", "Mediana", "Q1", "Q3"]}
            rows={MEDIANS}
          />
        </Stack>

        <Stack gap={8}>
          <H3>Segunda refatoração em relação à primeira</H3>
          <Text tone="secondary" size="small">
            Wilcoxon sem Holm (quatro comparações planejadas). Porcentagem:
            (duplicadas da 2 − duplicadas da 1) / duplicadas da 1 × 100.
          </Text>
          <Table striped headers={COMP_HEADERS} rows={COMPOSITION} />
        </Stack>
      </Stack>

      <Grid columns={2} gap={12}>
        <Card>
          <CardHeader>Saída</CardHeader>
          <CardBody>
            <Text>
              <Code>experiment-results/n/comparison.csv</Code>, os dois SVG e os
              CSV estatísticos na mesma pasta, inclusive{" "}
              <Code>shapiro-vs-original.csv</Code>.
            </Text>
          </CardBody>
        </Card>
        <Card>
          <CardHeader>Execução</CardHeader>
          <CardBody>
            <Text>
              <Code>./gradlew runExperimentN</Code> apaga{" "}
              <Code>experiment-results/n/</Code> e roda todos os sujeitos, com
              heap 32g. <Code>./gradlew runExperimentNMissing</Code> passa{" "}
              <Code>--missing-only</Code>: mantém o CSV e roda só os incompletos.{" "}
              <Code>./gradlew runExperimentNFromComparison</Code> passa{" "}
              <Code>--from-comparison</Code>: regenera os dois SVG e os CSV
              estatísticos a partir do <Code>comparison.csv</Code> já gravado,
              sem rodar o pipeline e sem alterar esse arquivo.
            </Text>
          </CardBody>
        </Card>
      </Grid>
    </Stack>
  );
}
