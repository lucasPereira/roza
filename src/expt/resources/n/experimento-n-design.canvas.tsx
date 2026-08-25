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

const VARIANTS = [
  "implicit",
  "residual-implicit",
  "delegated",
  "implicit+delegated",
  "delegated+implicit",
  "residual+delegated",
  "delegated+residual-implicit",
] as const;

const TREATMENTS = ["original", ...VARIANTS] as const;

const DASH = "—";

function dashedRow(label: string, cells: number): string[] {
  return [label, ...Array.from({ length: cells }, () => DASH)];
}

const PAIRWISE_ROWS: string[][] = VARIANTS.flatMap((a, i) =>
  VARIANTS.slice(i + 1).map((b) => dashedRow(`${a} vs ${b}`, 9)),
);

const COMPOSITION_PAIRS = [
  "implicit+delegated vs implicit",
  "delegated+implicit vs delegated",
  "residual+delegated vs residual-implicit",
  "delegated+residual-implicit vs delegated",
];

const VS_ORIGINAL_HEADERS = [
  "Variante",
  "W",
  "p",
  "Mediana da diferença",
  "IQR da diferença",
  "Mediana da %",
  "IQR da %",
  "Melhorou",
  "Piorou",
  "Empatou",
];

function MetricWilcoxonTable({
  titulo,
  coluna,
}: {
  titulo: string;
  coluna: string;
}) {
  return (
    <Stack gap={6}>
      <Text weight="semibold">
        {titulo} (<Code>{coluna}</Code>)
      </Text>
      <Table
        striped
        stickyHeader
        headers={VS_ORIGINAL_HEADERS}
        rows={VARIANTS.map((name) => dashedRow(name, 9))}
      />
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
            ["delegated", "sem implícita", "CCS", "Delegated setup", "—"],
            [
              "implicit+delegated",
              "com, depois sem",
              "LCCSS, depois CCS",
              "Implicit",
              "Delegated no resultado",
            ],
            [
              "delegated+implicit",
              "sem, depois com",
              "CCS, depois LCCSS",
              "Delegated",
              "Implicit no resultado",
            ],
            [
              "residual+delegated",
              "com, depois sem",
              "LCCSS, depois CCS",
              "Residual implicit",
              "Delegated no resultado",
            ],
            [
              "delegated+residual-implicit",
              "sem, depois com",
              "CCS, depois LCCSS",
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
          Uma linha por projeto e variante. A porcentagem de diferença na
          duplicação compara com o original daquele projeto:{" "}
          <Code>(duplicadas da variante − duplicadas do original) / duplicadas do original × 100</Code>.
          Número negativo significa que a duplicação caiu. Se o original não
          tiver duplicação, a célula fica vazia.
        </Text>
        <Table
          striped
          headers={["Coluna", "O que é"]}
          rows={[
            ["project", "Nome do sujeito"],
            ["variant", "original e as sete estratégias"],
            ["test_classes", "Quantas classes de teste"],
            ["setups", "Quantos métodos de configuração"],
            ["attributes", "Quantos atributos"],
            [
              "helper_methods",
              "Quantos métodos helper no total (nas classes de teste e nas classes helper)",
            ],
            ["total_statements", "Quantas sentenças de configuração"],
            ["duplicated_statements", "Quantas dessas sentenças estão repetidas"],
            ["duplication_rate", "duplicadas dividido pelo total, em %"],
            [
              "duplication_difference_percentage",
              "Quanto a duplicação mudou em relação ao original, em %",
            ],
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
          Três SVG gerados na execução. No eixo X, o projeto. Em cada projeto,
          uma barra por variante.
        </Text>
        <Table
          headers={["Arquivo", "Eixo Y"]}
          rows={[
            ["duplicated-statements.svg", "Sentenças duplicadas"],
            ["duplication-rate.svg", "Taxa de duplicação (%)"],
            [
              "duplication-difference-percentage.svg",
              "Porcentagem de diferença na duplicação vs original",
            ],
          ]}
        />
      </Stack>

      <Stack gap={10}>
        <H2>Estatística: o que cada teste devolve</H2>
        <Text>
          Primeiro o experimento aplica o teste de Shapiro-Wilk às diferenças
          entre cada variante e o original, nos 22 projetos, para identificar se
          essas diferenças seguem uma distribuição gaussiana. O teste publicado
          nas sete métricas é o Wilcoxon, de cada variante contra o original,
          mesmo quando alguma variante passar no Shapiro. Isso evita misturar t
          e Wilcoxon na mesma tabela. O Wilcoxon permite dizer se houve
          diferença entre a versão original e a refatorada e, se sim, quanto de
          diferença. Também se conta em quantos projetos o valor aumentou,
          diminuiu ou ficou igual. A porcentagem de diferença na duplicação
          permite quantificar quanto a duplicação mudou em relação ao original.
          A próxima etapa é comparar o original e as sete variantes. Aplica-se
          o teste de Friedman nas sentenças duplicadas para ver se existem
          diferenças entre eles. Se existir, compara-se par a par as sete
          variantes com Wilcoxon e correção de Holm para ver qual é melhor
          entre cada par. O original não entra nesses pares: já foi comparado
          com cada variante na etapa anterior. A porcentagem de diferença na
          duplicação de um versus o outro permite
          quantificar quanto a duplicação mudou entre o par. Também se conta em
          quantos projetos um melhorou, piorou ou empatou em relação ao outro. A
          melhor é
          a de menor mediana de sentenças duplicadas, desde que os pares
          mostrem que essa vantagem não é ruído. Por fim, nas composições, o
          Wilcoxon compara a suíte depois das duas refatorações com a suíte
          depois só da primeira, para ver se a segunda refatoração melhorou ou
          piorou. A porcentagem de diferença na duplicação da refatorada 1
          versus a refatorada 2 permite quantificar quanto a duplicação mudou
          nessa passagem.
        </Text>
        <Text>
          Nada disso vira uma porcentagem sozinha. Porcentagem é descritiva.
          Contra o original, usa-se a coluna{" "}
          <Code>duplication_difference_percentage</Code>. Na composição, a
          porcentagem é da refatorada 1 versus a refatorada 2:{" "}
          <Code>(duplicadas da 2 − duplicadas da 1) / duplicadas da 1 × 100</Code>
          . Nos pares do ranking, a mesma fórmula, um versus o outro. Teste de
          hipótese devolve sobretudo um p-valor. Wilcoxon contra o
          original roda nas sete colunas numéricas do CSV, não só em
          sentenças duplicadas. Em cada coluna, cada variante é um teste
          separado: 7 p-valores por coluna.
        </Text>
        <Callout tone="info" title="Exemplo: Wilcoxon em duplicated_statements">
          Para a variante implicit, há 22 pares (original vs implicit, um por
          projeto). O Wilcoxon desses 22 pares devolve: um p-valor, a estatística
          W, e a mediana das 22 diferenças (em sentenças, não em %). Repete isso
          para as outras 6 variantes. Essa coluna sozinha: 7 p-valores e 7
          medianas. O mesmo pacote se repete nas outras seis colunas.
        </Callout>
        <Table
          striped
          stickyHeader
          headers={["Procedimento", "Sobre o quê", "O que você anota"]}
          rows={[
            [
              "Shapiro–Wilk",
              "As 22 diferenças (variante − original), em cada uma das 7 colunas",
              "7 p-valores por coluna, um por variante. Não sai mediana nem %. Se qualquer variante daquela coluna tiver p pequeno, Wilcoxon vale para as sete variantes daquela coluna.",
            ],
            [
              "Wilcoxon vs original",
              "As 7 colunas: test_classes, setups, attributes, helper_methods, total_statements, duplicated_statements, duplication_rate",
              "7 linhas por coluna (uma por variante). Cada linha: p-valor, W, mediana da diferença na unidade da coluna. Não é %. duplication_difference_percentage fica de fora: no original é sempre 0.",
            ],
            [
              "Melhorou / piorou / empatou",
              "duplicated_statements",
              "7 trios de inteiros, um por variante. Exemplo implicit: 14 melhoraram, 5 pioraram, 3 empataram. Sem p-valor.",
            ],
            [
              "Mediana e IQR da porcentagem de diferença",
              "duplication_difference_percentage",
              "7 pares de números em %, um por variante. Exemplo implicit: mediana −18,4%, IQR −31 a −4. Sem p-valor (no original essa coluna é 0).",
            ],
            [
              "Friedman",
              "duplicated_statements do original e das 7 variantes nos 22 projetos",
              "1 p-valor e 1 χ². Só diz se os oito não são todos iguais. Não nomeia o melhor.",
            ],
            [
              "Wilcoxon entre pares, com Holm",
              "duplicated_statements, só se o Friedman der p pequeno",
              "Até 21 p-valores ajustados (7×6/2). Só entre variantes. Contra o original fica na tabela anterior.",
            ],
            [
              "Mediana e IQR da porcentagem de diferença em cada par",
              "Mesmos pares: (duplicadas de B − duplicadas de A) / duplicadas de A × 100",
              "Até 21 pares de números em %. A é a base do par. Se A não tiver duplicação, a célula fica vazia.",
            ],
            [
              "Melhorou / piorou / empatou por par",
              "duplicated_statements nos mesmos pares",
              "Até 21 trios. B melhorou se tiver menos duplicadas que A. Exemplo implicit vs delegated: 12 / 7 / 3. Sem p-valor.",
            ],
            [
              "Qual é a melhor",
              "Mediana de duplicated_statements nos 22 projetos, original e 7 variantes",
              "A de menor mediana. Se dois não diferirem no par com Holm, empatam. Sem p-valor extra.",
            ],
            [
              "Wilcoxon da composição vs a primeira sozinha",
              "Quatro pares: implicit+delegated vs implicit; delegated+implicit vs delegated; residual+delegated vs residual; delegated+residual-implicit vs delegated",
              "4 p-valores e 4 medianas em sentenças.",
            ],
            [
              "Mediana e IQR da porcentagem de diferença na composição",
              "Porcentagem da refatorada 1 versus a refatorada 2, nos mesmos quatro pares",
              "4 pares de números em %. Fórmula: (duplicadas da 2 − duplicadas da 1) / duplicadas da 1 × 100. Se a primeira não tiver duplicação, a célula fica vazia.",
            ],
            [
              "Melhorou / piorou / empatou na composição",
              "duplicated_statements, refatorada 2 versus refatorada 1",
              "4 trios. A segunda melhorou se tiver menos duplicadas que a primeira. Sem p-valor.",
            ],
          ]}
        />
        <Text>
          Resumo: Wilcoxon contra o original nas sete colunas. Sentenças
          duplicadas e taxa são o efeito; classes, setups, atributos, métodos
          helper e total de sentenças são o custo. A porcentagem de diferença
          contra o original não entra no Wilcoxon: mediana e IQR por variante.
          Friedman (e os pares com Holm) fica só em duplicated_statements, agora
          com o original e as sete variantes: a melhor é a de menor mediana. Em
          cada par entra também a porcentagem de diferença na duplicação, com a
          mesma fórmula da composição. Na composição, Wilcoxon e porcentagem de
          diferença usam a refatorada 1 versus a refatorada 2.
        </Text>
      </Stack>

      <Divider />

      <Stack gap={16}>
        <H2>Tabelas da tese</H2>
        <Text>
          Tabelas do capítulo de resultados. Depois de gravar{" "}
          <Code>comparison.csv</Code>, o próprio experimento calcula Wilcoxon,
          Friedman, Holm, medianas, IQR e melhorou/piorou/empatou e escreve um
          CSV por tabela em <Code>experiment-results/n/</Code>, junto com o
          CSV de comparação e os SVG. Não há
          passo extra em R. Células com travessão neste canvas são o molde;
          a execução preenche. Número negativo significa queda de duplicação
          (ou da métrica). Shapiro–Wilk fica no método, em uma frase, não em
          tabela.
        </Text>

        <Stack gap={8}>
          <H3>Comparação com o original: sentenças duplicadas</H3>
          <Text tone="secondary" size="small">
            Wilcoxon pareado, 22 projetos. A porcentagem é descritiva, não entra
            no teste. Melhorou = menos sentenças duplicadas que o original.
          </Text>
          <Table
            striped
            stickyHeader
            headers={VS_ORIGINAL_HEADERS}
            rows={VARIANTS.map((name) => dashedRow(name, 9))}
          />
        </Stack>

        <Stack gap={8}>
          <H3>Comparação com o original: demais métricas</H3>
          <Text tone="secondary" size="small">
            Mesmas colunas da tabela de sentenças duplicadas, uma tabela por
            métrica. Wilcoxon no valor da métrica. Melhorou = a métrica ficou
            menor que no original. Mediana e IQR da diferença estão nas 22
            diferenças (variante − original). Mediana e IQR da % usam
            (variante − original) / original × 100 nessa coluna. Se o original
            for zero, a % fica vazia. A taxa de duplicação (
            <Code>duplication_rate</Code>) não entra: a mediana da % na tabela
            de sentenças duplicadas já é a mudança relativa das duplicadas. A
            taxa (duplicadas / total) continua no CSV e no SVG, se precisar
            conferir se o volume de sentenças mudou a proporção.
          </Text>
          <MetricWilcoxonTable
            titulo="Classes de teste"
            coluna="test_classes"
          />
          <MetricWilcoxonTable titulo="Setups" coluna="setups" />
          <MetricWilcoxonTable titulo="Atributos" coluna="attributes" />
          <MetricWilcoxonTable
            titulo="Métodos helper"
            coluna="helper_methods"
          />
          <MetricWilcoxonTable
            titulo="Sentenças de configuração"
            coluna="total_statements"
          />
        </Stack>

        <Stack gap={8}>
          <H3>Friedman nas sentenças duplicadas</H3>
          <Text tone="secondary" size="small">
            Original e as sete variantes, 22 projetos. Um teste só. Se p não for
            pequeno, as tabelas de pares e de medianas não entram no texto.
          </Text>
          <Table
            headers={["χ²", "gl", "p"]}
            rows={[[DASH, "7", DASH]]}
          />
        </Stack>

        <Stack gap={8}>
          <H3>Comparações par a par nas sentenças duplicadas</H3>
          <Text tone="secondary" size="small">
            Wilcoxon com Holm, só entre as sete variantes (21 pares). A é a
            base da porcentagem: (B − A) / A × 100. B melhorou = menos
            duplicadas que A. Contra o original não entra: já está na primeira
            tabela.
          </Text>
          <Table
            striped
            stickyHeader
            headers={[
              "Par",
              "W",
              "p (Holm)",
              "Mediana da diferença",
              "IQR da diferença",
              "Mediana da %",
              "IQR da %",
              "Melhorou",
              "Piorou",
              "Empatou",
            ]}
            rows={PAIRWISE_ROWS}
          />
        </Stack>

        <Stack gap={8}>
          <H3>Medianas de sentenças duplicadas</H3>
          <Text tone="secondary" size="small">
            Só entra no texto se o Friedman e os pares com Holm derem p
            pequeno. Aqui a mediana não é a da diferença. É a mediana das 22
            contas de <Code>duplicated_statements</Code> de cada tratamento,
            o volume típico de duplicação, não o quanto mudou. Quem tem a menor
            mediana chegou com menos duplicação no projeto típico. Se implicit
            tiver mediana 40 e delegated 42, implicit “ganha” no número. Só
            declare isso se o par implicit vs delegated com Holm tiver p
            pequeno. Se o par não distinguir, os dois empatam no texto, mesmo
            com medianas diferentes.
          </Text>
          <Table
            striped
            headers={["Tratamento", "Mediana das sentenças duplicadas", "IQR"]}
            rows={TREATMENTS.map((name) => dashedRow(name, 2))}
          />
        </Stack>

        <Stack gap={8}>
          <H3>Segunda refatoração em relação à primeira</H3>
          <Text tone="secondary" size="small">
            Wilcoxon sem Holm (quatro comparações planejadas). Porcentagem:
            (duplicadas da 2 − duplicadas da 1) / duplicadas da 1 × 100. A
            segunda melhorou = menos duplicadas que a primeira.
          </Text>
          <Table
            striped
            headers={[
              "Par",
              "W",
              "p",
              "Mediana da diferença",
              "IQR da diferença",
              "Mediana da %",
              "IQR da %",
              "Melhorou",
              "Piorou",
              "Empatou",
            ]}
            rows={COMPOSITION_PAIRS.map((name) => dashedRow(name, 9))}
          />
        </Stack>
      </Stack>

      <Grid columns={2} gap={12}>
        <Card>
          <CardHeader>Saída</CardHeader>
          <CardBody>
            <Text>
              <Code>experiment-results/n/comparison.csv</Code>, os três SVG e,
              na mesma execução, os CSV das tabelas da tese em{" "}
              <Code>experiment-results/n/</Code>, junto com o CSV de comparação
              e os SVG.
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
              <Code>--missing-only</Code>: mantém o CSV e roda só os incompletos.
            </Text>
          </CardBody>
        </Card>
      </Grid>
    </Stack>
  );
}
