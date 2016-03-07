'use strict';

angular.module('databasestoreApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('calentador', {
                parent: 'entity',
                url: '/calentadors',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'databasestoreApp.calentador.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/calentador/calentadors.html',
                        controller: 'CalentadorController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('calentador');
                        $translatePartialLoader.addPart('enCal');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('calentador.detail', {
                parent: 'entity',
                url: '/calentador/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'databasestoreApp.calentador.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/calentador/calentador-detail.html',
                        controller: 'CalentadorDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('calentador');
                        $translatePartialLoader.addPart('enCal');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Calentador', function($stateParams, Calentador) {
                        return Calentador.get({id : $stateParams.id});
                    }]
                }
            })
            .state('calentador.new', {
                parent: 'calentador',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/calentador/calentador-dialog.html',
                        controller: 'CalentadorDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    modelo: null,
                                    Gas: null,
                                    litros: null,
                                    cantidad: null,
                                    comentario: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('calentador', null, { reload: true });
                    }, function() {
                        $state.go('calentador');
                    })
                }]
            })
            .state('calentador.edit', {
                parent: 'calentador',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/calentador/calentador-dialog.html',
                        controller: 'CalentadorDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Calentador', function(Calentador) {
                                return Calentador.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('calentador', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('calentador.delete', {
                parent: 'calentador',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/calentador/calentador-delete-dialog.html',
                        controller: 'CalentadorDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Calentador', function(Calentador) {
                                return Calentador.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('calentador', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
