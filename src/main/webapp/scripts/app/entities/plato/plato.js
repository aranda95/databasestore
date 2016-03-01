'use strict';

angular.module('databasestoreApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('plato', {
                parent: 'entity',
                url: '/platos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'databasestoreApp.plato.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/plato/platos.html',
                        controller: 'PlatoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('plato');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('plato.detail', {
                parent: 'entity',
                url: '/plato/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'databasestoreApp.plato.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/plato/plato-detail.html',
                        controller: 'PlatoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('plato');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Plato', function($stateParams, Plato) {
                        return Plato.get({id : $stateParams.id});
                    }]
                }
            })
            .state('plato.new', {
                parent: 'plato',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/plato/plato-dialog.html',
                        controller: 'PlatoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    marca: null,
                                    color: null,
                                    medidas: null,
                                    comentario: null,
                                    cantidad: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('plato', null, { reload: true });
                    }, function() {
                        $state.go('plato');
                    })
                }]
            })
            .state('plato.edit', {
                parent: 'plato',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/plato/plato-dialog.html',
                        controller: 'PlatoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Plato', function(Plato) {
                                return Plato.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('plato', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('plato.delete', {
                parent: 'plato',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/plato/plato-delete-dialog.html',
                        controller: 'PlatoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Plato', function(Plato) {
                                return Plato.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('plato', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
