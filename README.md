<div align="center">

# 🎲 QParty

**Настольная игра в формате мобильного приложения — с настройкой игроков, броском кубика и вопросами на компанию**

![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=flat&logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=flat&logo=jetpackcompose&logoColor=white)
![Min SDK](https://img.shields.io/badge/minSdk-24-brightgreen)
![Navigation](https://img.shields.io/badge/Navigation-Compose-orange)

</div>

QParty — Android-приложение для вечеринок и компаний: несколько игроков по очереди отвечают на вопросы из общей колоды, а кто ходит первым — решает бросок кубика. Написано полностью на Jetpack Compose, состояние экранов — на `ViewModel` + `StateFlow`.

## 📸 Скриншоты

<table>
<tr>
<td><img width="220" src="https://github.com/user-attachments/assets/45eb9cdd-65ba-48e4-a5fc-73e548a16cd0" /></td>
<td><img width="220" src="https://github.com/user-attachments/assets/57f87605-c637-4ff9-9c4e-98cb14b0b96b" /></td>
<td><img width="220" src="https://github.com/user-attachments/assets/8dbe3014-141e-4d66-9bbd-5a88d0b6bf16" /></td>
<td><img width="220" src="https://github.com/user-attachments/assets/1a617280-9607-446f-871a-e3e97a27891c" /></td>
</tr>
</table>

## 🎮 Игровой процесс

Приложение проводит игроков через четыре экрана по цепочке:

1. **Старт** — приветственный экран с названием игры и кнопкой начала
2. **Настройка игроков** — ввод количества участников и имени каждого
3. **Бросок кубика** — каждый игрок по очереди бросает кубик (анимированная 3D-кость на `Canvas`); игрок с наибольшим значением ходит первым
4. **Игра** — вопросы показываются по одному на карточке; после ответа игрок нажимает «Далее», а когда вопросы заканчиваются — доступен перезапуск с новой подборкой

Данные между экранами передаются через `NavController.savedStateHandle`, поэтому имена игроков и результат броска кубика доживают до игрового экрана без глобального состояния.

## ✨ Особенности

- 🎲 **Бросок кубика** для определения первого хода — анимация вращения на `Canvas` + `Animatable`
- 👥 **Гибкая настройка** количества игроков и их имён перед стартом
- 🗂️ **Банк вопросов** — три JSON-набора в `assets/`, при каждой игре вопросы перемешиваются и не повторяются, пока не закончится колода
- 🎨 **Material 3 UI**: карточки вопросов, кнопки с иконками, анимированный волнообразный фон на игровом экране
- 🔄 **Плавные переходы** между вопросами через `AnimatedContent` (fade in/out)
- 🌙 **Тёмная и светлая темы**, выбор сохраняется между запусками через `DataStore Preferences`
- 🔁 **Перезапуск игры** без потери настроек игроков

## 🏗 Архитектура

MVVM с однонаправленным потоком данных: экраны на Compose подписываются на `StateFlow` из соответствующих `ViewModel`, которые не содержат ссылок на UI.

```
com.example.qparty
├── model/            # Player, Question — доменные модели
├── data/             # ThemeRepository(Interface) — хранение темы в DataStore
├── util/             # QuestionRepository — загрузка и перемешивание вопросов из assets
├── navigation/        # NavGraph и маршруты (Routes)
├── viewmodel/
│   ├── GameSetupViewModel   # список игроков, бросок кубика, определение первого игрока
│   ├── QuestionViewModel    # текущий вопрос, очередность ходов, перезапуск игры
│   └── ThemeViewModel       # переключение и персистентность темы
└── ui/
    ├── screens/       # StartScreen, PlayerSetupScreen, DiceRollScreen, GameScreen
    ├── components/    # переиспользуемые Composable (TopBar и т.д.)
    └── theme/         # цвета, типографика, Material 3 тема
```

Вопросы лежат в `app/src/main/assets/questions1–3.json` и объединяются в единый пул при загрузке — так колоду легко расширять, просто добавив новый JSON-файл в список `QuestionRepository`.

## 🧪 Тесты

Проект покрыт как unit-, так и инструментальными тестами:

- `ThemeViewModelTest` — unit-тест переключения темы
- `QuestionViewModelTest`, `NavigationTest`, `ThemeUITest`, `StartScreenTest`, `GameScreenTest` — Espresso/Compose UI-тесты экранов и навигации

```bash
./gradlew test                    # unit-тесты
./gradlew connectedAndroidTest    # инструментальные UI-тесты
```

## 🛠 Стек технологий

`Kotlin` · `Jetpack Compose` · `Material 3` · `Navigation Compose` · `ViewModel` + `StateFlow` · `Kotlinx Serialization` · `DataStore Preferences` · `Coroutines` · `JUnit` · `Espresso` · `Compose UI Test`

## 🚀 Запуск

```bash
git clone https://github.com/ChristielBel/qparty.git
```

1. Откройте проект в **Android Studio**
2. Синхронизируйте Gradle
3. Запустите на устройстве или эмуляторе (API 24+)

Требования: JDK 11, compileSdk 36.
