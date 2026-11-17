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
  [
    "duplicated_statements",
    "implicit",
    "18",
    "0.7070",
    "9.630e-05",
    "não"
  ],
  [
    "duplicated_statements",
    "residual-implicit",
    "18",
    "0.7264",
    "0.0001647",
    "não"
  ],
  [
    "duplicated_statements",
    "delegated",
    "18",
    "0.7629",
    "0.0004758",
    "não"
  ],
  [
    "duplicated_statements",
    "implicit+delegated",
    "18",
    "0.7704",
    "0.0005975",
    "não"
  ],
  [
    "duplicated_statements",
    "delegated+implicit",
    "18",
    "0.7566",
    "0.0003945",
    "não"
  ],
  [
    "duplicated_statements",
    "residual-implicit+delegated",
    "18",
    "0.7852",
    "0.0009448",
    "não"
  ],
  [
    "duplicated_statements",
    "delegated+residual-implicit",
    "18",
    "0.7807",
    "0.0008221",
    "não"
  ],
  [
    "test_classes",
    "implicit",
    "18",
    "0.7991",
    "0.001477",
    "não"
  ],
  [
    "test_classes",
    "residual-implicit",
    "18",
    "0.7818",
    "0.0008504",
    "não"
  ],
  [
    "test_classes",
    "delegated",
    "18",
    "0.3193",
    "3.059e-08",
    "não"
  ],
  [
    "test_classes",
    "implicit+delegated",
    "18",
    "0.7991",
    "0.001477",
    "não"
  ],
  [
    "test_classes",
    "delegated+implicit",
    "18",
    "0.8051",
    "0.001795",
    "não"
  ],
  [
    "test_classes",
    "residual-implicit+delegated",
    "18",
    "0.7818",
    "0.0008504",
    "não"
  ],
  [
    "test_classes",
    "delegated+residual-implicit",
    "18",
    "0.7848",
    "0.0009325",
    "não"
  ],
  [
    "setup_methods",
    "implicit",
    "18",
    "0.8146",
    "0.002457",
    "não"
  ],
  [
    "setup_methods",
    "residual-implicit",
    "18",
    "0.8091",
    "0.002045",
    "não"
  ],
  [
    "setup_methods",
    "delegated",
    "18",
    "0.3772",
    "8.141e-08",
    "não"
  ],
  [
    "setup_methods",
    "implicit+delegated",
    "18",
    "0.8146",
    "0.002457",
    "não"
  ],
  [
    "setup_methods",
    "delegated+implicit",
    "18",
    "0.8182",
    "0.002775",
    "não"
  ],
  [
    "setup_methods",
    "residual-implicit+delegated",
    "18",
    "0.8091",
    "0.002045",
    "não"
  ],
  [
    "setup_methods",
    "delegated+residual-implicit",
    "18",
    "0.8106",
    "0.002150",
    "não"
  ],
  [
    "attributes",
    "implicit",
    "18",
    "0.8179",
    "0.002752",
    "não"
  ],
  [
    "attributes",
    "residual-implicit",
    "18",
    "0.7710",
    "0.0006075",
    "não"
  ],
  [
    "attributes",
    "delegated",
    "18",
    "0.3418",
    "4.440e-08",
    "não"
  ],
  [
    "attributes",
    "implicit+delegated",
    "18",
    "0.8179",
    "0.002752",
    "não"
  ],
  [
    "attributes",
    "delegated+implicit",
    "18",
    "0.8359",
    "0.005110",
    "não"
  ],
  [
    "attributes",
    "residual-implicit+delegated",
    "18",
    "0.7710",
    "0.0006075",
    "não"
  ],
  [
    "attributes",
    "delegated+residual-implicit",
    "18",
    "0.7724",
    "0.0006342",
    "não"
  ],
  [
    "helper_methods",
    "implicit",
    "18",
    "0.2527",
    "1.058e-08",
    "não"
  ],
  [
    "helper_methods",
    "residual-implicit",
    "18",
    "0.2527",
    "1.058e-08",
    "não"
  ],
  [
    "helper_methods",
    "delegated",
    "18",
    "0.7868",
    "0.0009956",
    "não"
  ],
  [
    "helper_methods",
    "implicit+delegated",
    "18",
    "0.8216",
    "0.003114",
    "não"
  ],
  [
    "helper_methods",
    "delegated+implicit",
    "18",
    "0.7868",
    "0.0009956",
    "não"
  ],
  [
    "helper_methods",
    "residual-implicit+delegated",
    "18",
    "0.8122",
    "0.002273",
    "não"
  ],
  [
    "helper_methods",
    "delegated+residual-implicit",
    "18",
    "0.7868",
    "0.0009956",
    "não"
  ],
  [
    "total_statements",
    "implicit",
    "18",
    "0.6949",
    "6.964e-05",
    "não"
  ],
  [
    "total_statements",
    "residual-implicit",
    "18",
    "0.7326",
    "0.0001960",
    "não"
  ],
  [
    "total_statements",
    "delegated",
    "18",
    "0.7589",
    "0.0004223",
    "não"
  ],
  [
    "total_statements",
    "implicit+delegated",
    "18",
    "0.7673",
    "0.0005431",
    "não"
  ],
  [
    "total_statements",
    "delegated+implicit",
    "18",
    "0.7449",
    "0.0002795",
    "não"
  ],
  [
    "total_statements",
    "residual-implicit+delegated",
    "18",
    "0.7862",
    "0.0009753",
    "não"
  ],
  [
    "total_statements",
    "delegated+residual-implicit",
    "18",
    "0.7816",
    "0.0008436",
    "não"
  ]
] as string[][];

const VS_DUPLICATED = [
  [
    "implicit",
    "10.00",
    "0.001642",
    "-62.50",
    "-311.8",
    "-18.50",
    "-9.187",
    "-19.63",
    "-3.880",
    "1",
    "16",
    "1"
  ],
  [
    "residual-implicit",
    "0.000",
    "0.0002925",
    "-64.00",
    "-569.5",
    "-35.00",
    "-12.18",
    "-22.67",
    "-7.389",
    "0",
    "17",
    "1"
  ],
  [
    "delegated",
    "0.000",
    "0.0004378",
    "-109.5",
    "-211.3",
    "-30.25",
    "-10.73",
    "-14.76",
    "-6.013",
    "0",
    "16",
    "2"
  ],
  [
    "implicit+delegated",
    "0.000",
    "0.0001964",
    "-130.0",
    "-583.8",
    "-32.75",
    "-18.58",
    "-27.25",
    "-14.01",
    "0",
    "18",
    "0"
  ],
  [
    "delegated+implicit",
    "3.000",
    "0.0003270",
    "-126.5",
    "-517.5",
    "-32.75",
    "-17.85",
    "-26.16",
    "-10.13",
    "1",
    "17",
    "0"
  ],
  [
    "residual-implicit+delegated",
    "0.000",
    "0.0001964",
    "-148.0",
    "-654.8",
    "-57.25",
    "-21.55",
    "-29.89",
    "-16.40",
    "0",
    "18",
    "0"
  ],
  [
    "delegated+residual-implicit",
    "0.000",
    "0.0001964",
    "-149.5",
    "-630.5",
    "-55.00",
    "-20.28",
    "-28.43",
    "-14.90",
    "0",
    "18",
    "0"
  ]
] as string[][];

const VS_CLASSES = [
  [
    "implicit",
    "1.000",
    "0.0003515",
    "189.5",
    "43.50",
    "789.5",
    "245.5",
    "150.2",
    "309.3",
    "16",
    "1",
    "1"
  ],
  [
    "residual-implicit",
    "3.000",
    "0.0005017",
    "23.50",
    "8.250",
    "113.0",
    "40.20",
    "20.02",
    "59.37",
    "15",
    "2",
    "1"
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
    "16"
  ],
  [
    "implicit+delegated",
    "1.000",
    "0.0003515",
    "189.5",
    "43.50",
    "789.5",
    "245.5",
    "150.2",
    "309.3",
    "16",
    "1",
    "1"
  ],
  [
    "delegated+implicit",
    "1.000",
    "0.0003509",
    "188.0",
    "33.75",
    "744.5",
    "239.5",
    "121.9",
    "292.9",
    "16",
    "1",
    "1"
  ],
  [
    "residual-implicit+delegated",
    "3.000",
    "0.0005017",
    "23.50",
    "8.250",
    "113.0",
    "40.20",
    "20.02",
    "59.37",
    "15",
    "2",
    "1"
  ],
  [
    "delegated+residual-implicit",
    "4.500",
    "0.0006524",
    "22.50",
    "5.000",
    "109.0",
    "40.20",
    "7.120",
    "58.51",
    "15",
    "2",
    "1"
  ]
] as string[][];

const VS_SETUPS = [
  [
    "implicit",
    "6.000",
    "0.001344",
    "42.00",
    "8.250",
    "182.5",
    "276.5",
    "53.85",
    "588.9",
    "15",
    "1",
    "2"
  ],
  [
    "residual-implicit",
    "0.000",
    "0.0002925",
    "41.00",
    "13.00",
    "149.0",
    "328.6",
    "112.5",
    "650.0",
    "17",
    "0",
    "1"
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
    "16"
  ],
  [
    "implicit+delegated",
    "6.000",
    "0.001344",
    "42.00",
    "8.250",
    "182.5",
    "276.5",
    "53.85",
    "588.9",
    "15",
    "1",
    "2"
  ],
  [
    "delegated+implicit",
    "5.000",
    "0.001123",
    "42.50",
    "8.250",
    "173.3",
    "266.7",
    "53.85",
    "566.7",
    "15",
    "1",
    "2"
  ],
  [
    "residual-implicit+delegated",
    "0.000",
    "0.0002925",
    "41.00",
    "13.00",
    "149.0",
    "328.6",
    "112.5",
    "650.0",
    "17",
    "0",
    "1"
  ],
  [
    "delegated+residual-implicit",
    "0.000",
    "0.0002919",
    "44.50",
    "13.00",
    "145.0",
    "335.7",
    "112.5",
    "650.0",
    "17",
    "0",
    "1"
  ]
] as string[][];

const VS_ATTRIBUTES = [
  [
    "implicit",
    "32.00",
    "0.03513",
    "19.00",
    "0.7500",
    "194.5",
    "28.57",
    "0.000",
    "55.40",
    "13",
    "4",
    "1"
  ],
  [
    "residual-implicit",
    "0.000",
    "0.0002925",
    "64.00",
    "14.25",
    "349.0",
    "67.37",
    "15.38",
    "106.0",
    "17",
    "0",
    "1"
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
    "16"
  ],
  [
    "implicit+delegated",
    "32.00",
    "0.03513",
    "19.00",
    "0.7500",
    "194.5",
    "28.57",
    "0.000",
    "55.40",
    "13",
    "4",
    "1"
  ],
  [
    "delegated+implicit",
    "35.00",
    "0.04947",
    "12.50",
    "0.7500",
    "104.3",
    "22.77",
    "0.000",
    "38.00",
    "13",
    "4",
    "1"
  ],
  [
    "residual-implicit+delegated",
    "0.000",
    "0.0002925",
    "64.00",
    "14.25",
    "349.0",
    "67.37",
    "15.38",
    "106.0",
    "17",
    "0",
    "1"
  ],
  [
    "delegated+residual-implicit",
    "0.000",
    "0.0002925",
    "43.00",
    "11.00",
    "290.5",
    "55.62",
    "17.95",
    "85.71",
    "17",
    "0",
    "1"
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
    "17"
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
    "17"
  ],
  [
    "delegated",
    "3.000",
    "0.0007748",
    "24.50",
    "6.500",
    "82.75",
    "18.43",
    "5.465",
    "32.80",
    "15",
    "1",
    "2"
  ],
  [
    "implicit+delegated",
    "8.000",
    "0.001915",
    "15.50",
    "6.250",
    "69.25",
    "14.11",
    "6.431",
    "30.67",
    "15",
    "1",
    "2"
  ],
  [
    "delegated+implicit",
    "3.000",
    "0.0007748",
    "24.50",
    "6.500",
    "82.75",
    "18.43",
    "5.465",
    "32.80",
    "15",
    "1",
    "2"
  ],
  [
    "residual-implicit+delegated",
    "8.000",
    "0.001912",
    "15.50",
    "6.250",
    "56.00",
    "13.87",
    "5.598",
    "23.62",
    "15",
    "1",
    "2"
  ],
  [
    "delegated+residual-implicit",
    "3.000",
    "0.0007748",
    "24.50",
    "6.500",
    "82.75",
    "18.43",
    "5.465",
    "32.80",
    "15",
    "1",
    "2"
  ]
] as string[][];

const VS_TOTAL = [
  [
    "implicit",
    "18.00",
    "0.003282",
    "-56.00",
    "-246.5",
    "-14.75",
    "-2.435",
    "-6.039",
    "-0.7079",
    "3",
    "15",
    "0"
  ],
  [
    "residual-implicit",
    "0.000",
    "0.0002925",
    "-61.00",
    "-536.8",
    "-28.75",
    "-4.192",
    "-7.149",
    "-2.284",
    "0",
    "17",
    "1"
  ],
  [
    "delegated",
    "0.000",
    "0.0004378",
    "-88.00",
    "-166.8",
    "-17.25",
    "-3.012",
    "-3.431",
    "-1.292",
    "0",
    "16",
    "2"
  ],
  [
    "implicit+delegated",
    "7.000",
    "0.0006292",
    "-112.5",
    "-480.8",
    "-22.25",
    "-5.413",
    "-7.788",
    "-2.549",
    "1",
    "17",
    "0"
  ],
  [
    "delegated+implicit",
    "9.000",
    "0.0008635",
    "-105.0",
    "-405.3",
    "-22.25",
    "-5.122",
    "-6.876",
    "-2.243",
    "2",
    "16",
    "0"
  ],
  [
    "residual-implicit+delegated",
    "0.000",
    "0.0001964",
    "-127.5",
    "-629.8",
    "-43.75",
    "-6.400",
    "-9.104",
    "-4.869",
    "0",
    "18",
    "0"
  ],
  [
    "delegated+residual-implicit",
    "0.000",
    "0.0001964",
    "-131.0",
    "-580.8",
    "-40.25",
    "-5.761",
    "-8.150",
    "-4.383",
    "0",
    "18",
    "0"
  ]
] as string[][];

const FRIEDMAN = [
  [
    "95.82",
    "7",
    "4.923e-13"
  ]
] as string[][];

const PAIRWISE = [
  [
    "implicit vs residual-implicit",
    "0.000",
    "0.008493",
    "-20.00",
    "-119.0",
    "-4.750",
    "-3.388",
    "-4.629",
    "-1.197",
    "0",
    "15",
    "3"
  ],
  [
    "implicit vs delegated",
    "78.00",
    "0.7439",
    "-3.000",
    "-19.00",
    "108.8",
    "-0.5997",
    "-6.558",
    "10.96",
    "8",
    "10",
    "0"
  ],
  [
    "implicit vs implicit+delegated",
    "0.000",
    "0.007409",
    "-72.00",
    "-159.0",
    "-17.50",
    "-10.17",
    "-11.89",
    "-5.273",
    "0",
    "16",
    "2"
  ],
  [
    "implicit vs delegated+implicit",
    "0.000",
    "0.007409",
    "-69.00",
    "-117.3",
    "-27.25",
    "-7.721",
    "-11.03",
    "-4.051",
    "0",
    "16",
    "2"
  ],
  [
    "implicit vs residual-implicit+delegated",
    "0.000",
    "0.006142",
    "-74.50",
    "-278.3",
    "-29.50",
    "-12.57",
    "-15.35",
    "-8.599",
    "0",
    "17",
    "1"
  ],
  [
    "implicit vs delegated+residual-implicit",
    "0.000",
    "0.006142",
    "-78.00",
    "-178.0",
    "-30.50",
    "-10.71",
    "-14.38",
    "-7.418",
    "0",
    "17",
    "1"
  ],
  [
    "residual-implicit vs delegated",
    "41.50",
    "0.2925",
    "10.50",
    "-4.250",
    "268.5",
    "2.373",
    "-1.568",
    "14.92",
    "10",
    "7",
    "1"
  ],
  [
    "residual-implicit vs implicit+delegated",
    "12.50",
    "0.01712",
    "-20.50",
    "-92.75",
    "-2.750",
    "-4.124",
    "-8.152",
    "-0.4837",
    "3",
    "14",
    "1"
  ],
  [
    "residual-implicit vs delegated+implicit",
    "42.00",
    "0.2925",
    "-10.00",
    "-58.00",
    "4.500",
    "-2.762",
    "-7.410",
    "0.1842",
    "5",
    "12",
    "1"
  ],
  [
    "residual-implicit vs residual-implicit+delegated",
    "0.000",
    "0.007409",
    "-71.00",
    "-132.0",
    "-16.50",
    "-8.769",
    "-10.65",
    "-4.471",
    "0",
    "16",
    "2"
  ],
  [
    "residual-implicit vs delegated+residual-implicit",
    "0.000",
    "0.007409",
    "-52.00",
    "-115.8",
    "-22.50",
    "-7.792",
    "-9.800",
    "-3.603",
    "0",
    "16",
    "2"
  ],
  [
    "delegated vs implicit+delegated",
    "10.00",
    "0.01609",
    "-42.00",
    "-345.5",
    "-17.50",
    "-9.838",
    "-18.96",
    "-4.038",
    "1",
    "16",
    "1"
  ],
  [
    "delegated vs delegated+implicit",
    "11.00",
    "0.01609",
    "-29.50",
    "-234.8",
    "-17.00",
    "-8.338",
    "-18.34",
    "-3.790",
    "1",
    "16",
    "1"
  ],
  [
    "delegated vs residual-implicit+delegated",
    "0.000",
    "0.006142",
    "-44.50",
    "-447.0",
    "-30.25",
    "-11.86",
    "-21.43",
    "-6.817",
    "0",
    "17",
    "1"
  ],
  [
    "delegated vs delegated+residual-implicit",
    "0.000",
    "0.006142",
    "-42.00",
    "-422.8",
    "-30.25",
    "-9.933",
    "-20.45",
    "-6.723",
    "0",
    "17",
    "1"
  ],
  [
    "implicit+delegated vs delegated+implicit",
    "9.500",
    "0.06593",
    "4.500",
    "0.000",
    "39.50",
    "0.5498",
    "0.000",
    "1.908",
    "11",
    "2",
    "5"
  ],
  [
    "implicit+delegated vs residual-implicit+delegated",
    "0.000",
    "0.008493",
    "-18.50",
    "-88.00",
    "-4.000",
    "-3.148",
    "-3.908",
    "-1.238",
    "0",
    "15",
    "3"
  ],
  [
    "implicit+delegated vs delegated+residual-implicit",
    "12.00",
    "0.06593",
    "-12.00",
    "-41.25",
    "0.000",
    "-1.931",
    "-2.905",
    "0.000",
    "2",
    "12",
    "4"
  ],
  [
    "delegated+implicit vs residual-implicit+delegated",
    "7.000",
    "0.01609",
    "-17.00",
    "-138.3",
    "-3.250",
    "-3.384",
    "-6.604",
    "-1.136",
    "1",
    "15",
    "2"
  ],
  [
    "delegated+implicit vs delegated+residual-implicit",
    "1.000",
    "0.008836",
    "-15.50",
    "-85.25",
    "-7.250",
    "-2.905",
    "-3.690",
    "-1.350",
    "1",
    "14",
    "3"
  ],
  [
    "residual-implicit+delegated vs delegated+residual-implicit",
    "13.00",
    "0.09235",
    "2.500",
    "0.000",
    "27.75",
    "0.5621",
    "0.000",
    "1.438",
    "11",
    "2",
    "5"
  ]
] as string[][];

const MEDIANS = [
  [
    "original",
    "974.0",
    "312.0",
    "2686"
  ],
  [
    "implicit",
    "672.0",
    "290.5",
    "2324"
  ],
  [
    "residual-implicit",
    "660.0",
    "279.3",
    "2198"
  ],
  [
    "delegated",
    "820.5",
    "308.5",
    "2482"
  ],
  [
    "implicit+delegated",
    "617.0",
    "282.5",
    "2104"
  ],
  [
    "delegated+implicit",
    "611.5",
    "282.3",
    "2217"
  ],
  [
    "residual-implicit+delegated",
    "606.0",
    "275.8",
    "2005"
  ],
  [
    "delegated+residual-implicit",
    "598.0",
    "275.8",
    "2122"
  ]
] as string[][];

const COMPOSITION = [
  [
    "implicit+delegated vs implicit",
    "0.000",
    "0.0004358",
    "-72.00",
    "-159.0",
    "-17.50",
    "-10.17",
    "-11.89",
    "-5.273",
    "0",
    "16",
    "2"
  ],
  [
    "delegated+implicit vs delegated",
    "11.00",
    "0.001920",
    "-29.50",
    "-234.8",
    "-17.00",
    "-8.338",
    "-18.34",
    "-3.790",
    "1",
    "16",
    "1"
  ],
  [
    "residual-implicit+delegated vs residual-implicit",
    "0.000",
    "0.0004358",
    "-71.00",
    "-132.0",
    "-16.50",
    "-8.769",
    "-10.65",
    "-4.471",
    "0",
    "16",
    "2"
  ],
  [
    "delegated+residual-implicit vs delegated",
    "0.000",
    "0.0002925",
    "-42.00",
    "-422.8",
    "-30.25",
    "-9.933",
    "-20.45",
    "-6.723",
    "0",
    "17",
    "1"
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
          das duas primeiras com a delegada, em 17 bibliotecas selecionadas de{" "}
          <Code>external-projects/</Code> mais o Róża. Cada sujeito gera uma linha
          por variante no CSV.
        </Text>
      </Stack>

      <Grid columns={4} gap={16}>
        <Stat value="18" label="Sujeitos (17 libs + Róża)" />
        <Stat value="8" label="Linhas por sujeito (original + 7 variantes)" />
        <Stat value="144" label="Linhas no CSV" />
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
          Quem tem várias pastas de teste entra como um sujeito só: o loader
          recebe todas elas.
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
          ]}
        />
        <Text tone="secondary" size="small">
          Se um sujeito não tiver nenhum teste parseável, registra e segue.
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
          inteiro, por exemplo <Code>[selenium] implicit 42% | total 18%</Code>.
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
          Shapiro–Wilk entra como diagnóstico nas 18 diferenças (variante −
          original) de cada métrica e cada variante, com α = 0,05. Amostra
          constante deixa W e p vazios. O teste publicado é o Wilcoxon pareado,
          escolhido a priori: 18 pares (um por sujeito), métricas de contagem,
          zeros e assimetria. Não se troca para t de Student, mesmo se o Shapiro
          não rejeitar. Wilcoxon contra o original roda nas
          seis colunas numéricas do CSV. Cada variante é um teste separado: 7
          p-valores por coluna. Conta-se em quantos projetos o valor aumentou,
          diminuiu ou ficou igual. A porcentagem é descritiva, não entra no
          teste:{" "}
          <Code>(variante − original) / original × 100</Code>. Q1 e Q3 são os
          quartis das 18 diferenças (e das 18 porcentagens).
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
          Para a variante implicit, há 18 diferenças (implicit − original, um por
          projeto). O Shapiro–Wilk dessas 18 diferenças devolve W e p; se p for
          menor que 0,05, a coluna Normal fica “não”. O Wilcoxon desses 18 pares
          devolve: um p-valor, a estatística W, e a mediana das 18 diferenças
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
              "As 6 colunas, nas 18 diferenças (variante − original)",
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
              "7 trios de inteiros, um por variante. Exemplo implicit em duplicated_statements: 1 aumentou, 16 diminuíram, 1 ficou igual.",
            ],
            [
              "Mediana, Q1 e Q3 da porcentagem",
              "duplicated_statements vs original: (variante − original) / original × 100",
              "7 trios em %. Se o original não tiver duplicação, a célula fica vazia.",
            ],
            [
              "Friedman",
              "duplicated_statements do original e das 7 variantes nos 18 projetos",
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
              "Mediana de duplicated_statements nos 18 projetos, original e 7 variantes",
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
          Fonte: <Code>experiment-results/n/*.csv</Code>, 18 sujeitos.
        </Text>

        <Stack gap={8}>
          <H3>Shapiro–Wilk das diferenças vs original</H3>
          <Text tone="secondary" size="small">
            Diagnóstico de normalidade nas 18 diferenças (variante − original),
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
            nas 18 diferenças (variante − original). A % usa (variante −
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
            Original e as sete variantes, 18 projetos. Um teste só.
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
            Mediana, Q1 e Q3 das 18 contas de <Code>duplicated_statements</Code>{" "}
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
              Os CSV primários <Code>comparison.csv</Code> e{" "}
              <Code>original-tests.csv</Code>, o relatório <Code>skipped.csv</Code>,
              os dois SVG e os CSV estatísticos ficam em{" "}
              <Code>experiment-results/n/</Code>.
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
              <Code>--missing-only</Code>: mantém os CSV primários e roda só os
              incompletos; cada sujeito retido precisa ter sua contagem em{" "}
              <Code>original-tests.csv</Code>.{" "}
              <Code>./gradlew runExperimentNFromComparison</Code> passa{" "}
              <Code>--from-comparison</Code>: regenera os dois SVG e os CSV
              estatísticos a partir do <Code>comparison.csv</Code> já gravado,
              sem rodar o pipeline nem alterar os CSV primários.
            </Text>
          </CardBody>
        </Card>
      </Grid>
    </Stack>
  );
}
