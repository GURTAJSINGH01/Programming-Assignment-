
DigitalSignalSimulator/
├── pom.xml
├── README.md
├── .gitignore
│
├── docs/
│   └── specification-report.pdf
│
└── src/
    ├── main/
    │   ├── java/
    │   │   └── com/
    │   │       └── signalsimulator/
    │   │           ├── Main.java
    │   │           │
    │   │           ├── app/
    │   │           │   └── ApplicationController.java
    │   │           │
    │   │           ├── model/
    │   │           │   ├── EncodedSignal.java
    │   │           │   ├── DigitalData.java
    │   │           │   ├── AnalogSignal.java
    │   │           │   └── EncodingType.java
    │   │           │
    │   │           ├── generator/
    │   │           │   └── DataGenerator.java
    │   │           │
    │   │           ├── algorithms/
    │   │           │   └── PalindromeFinder.java
    │   │           │
    │   │           ├── encoder/
    │   │           │   ├── LineEncoder.java
    │   │           │   ├── NRZLEncoder.java
    │   │           │   ├── NRZIEncoder.java
    │   │           │   ├── ManchesterEncoder.java
    │   │           │   ├── DifferentialManchesterEncoder.java
    │   │           │   ├── AMIEncoder.java
    │   │           │   └── Scrambler.java
    │   │           │
    │   │           ├── decoder/
    │   │           │   └── LineDecoder.java
    │   │           │
    │   │           ├── modulation/
    │   │           │   ├── PCMEncoder.java
    │   │           │   ├── PCMDecoder.java
    │   │           │   ├── DeltaModulationEncoder.java
    │   │           │   └── DeltaModulationDecoder.java
    │   │           │
    │   │           ├── visualization/
    │   │           │   └── SignalPlotter.java
    │   │           │
    │   │           ├── service/
    │   │           │   └── SimulationService.java
    │   │           │
    │   │           └── exception/
    │   │               └── InvalidSignalException.java
    │   │
    │   └── resources/
    │       └── com/
    │           └── signalsimulator/
    │               ├── main-view.fxml
    │               └── styles.css
    │
    └── test/
        └── java/
            └── com/
                └── signalsimulator/
                    ├── generator/
                    │   └── DataGeneratorTest.java
                    ├── encoder/
                    │   └── LineEncoderTest.java
                    ├── decoder/
                    │   └── LineDecoderTest.java
                    └── modulation/
                        └── ModulationTest.java
