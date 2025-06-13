package ru.akumakeito.currention.ui.screens

//
//@Composable
//fun UsualConverterScreen(
//    currencyList: List<String>
//) {
//    val usualConverterViewModel: UsualConverterViewModel = hiltViewModel()
//    val convertingCurrencyState by usualConverterViewModel.convertingCurrencyState.collectAsState()
//
//            ConvertingCurrencyRow(
//                firstCurrency = convertingCurrencyState.firstCurrency,
//                secondCurrency = convertingCurrencyState.secondCurrency,
//                rate = convertingCurrencyState.rateFromFirstToSecond,
//                amount = convertingCurrencyState.amount,
//                readOnly = false,
//                currencyList = currencyList,
//                onCurrencyItemDropDownClickListener = { selectedCurrency ->
//                    usualConverterViewModel.updatePairCurrencyFrom(selectedCurrency)
//                },
//                onSearchTextChanged = { usualConverterViewModel.onSearchTextChange(it) },
//                onAmountTextChanged = { amount ->
//                    coroutineScope.launch {
//                        usualConverterViewModel.changeAmount(amount)
//                    }
//                },
//                onAmountDone = { usualConverterViewModel.convert() },
//                onClearAmount = { usualConverterViewModel.clearAmount() }
//            )
//
//            Box(contentAlignment = Alignment.Center) {
//
//                HorizontalDivider()
//                IconButton(onClick = { usualConverterViewModel.reversePairCurrency() }) {
//                    Image(
//                        painter = painterResource(id = R.drawable.ic_button_swap),
//                        contentDescription = null
//                    )
//                }
//            }
//
//            ConvertingCurrencyRow(
//                firstCurrency = convertingCurrencyState.secondCurrency,
//                secondCurrency = convertingCurrencyState.firstCurrency,
//                rate = convertingCurrencyState.rateFromSecondToFirst,
//                amount = convertingCurrencyState.convertedAmount,
//                readOnly = true,
//                currencyList = currencyList,
//                onCurrencyItemDropDownClickListener = { selectedCurrency ->
//                    usualConverterViewModel.updatePairCurrencyTo(selectedCurrency)
//                },
//                onSearchTextChanged = { usualConverterViewModel.onSearchTextChange(it) },
//                onAmountTextChanged = {},
//                onAmountDone = {},
//                onClearAmount = {}
//
//            )
//            HorizontalDivider()
//
//
////            KeyboardScreen()
////            NumberKeyboard(
////                onNumberClick = { amount ->
////                    coroutineScope.launch {
////                        convertCurrencyViewModel.changeAmount(amount)
////                    }
////                },
////                onDoneClick = { convertCurrencyViewModel.convert() },
////                onBackspaceClick = {
////                    coroutineScope.launch {
////                        convertCurrencyViewModel.changeAmount("")
////                    }
////                }
////            )
//        }
//    }
//}}
