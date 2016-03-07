'use strict';

angular.module('databasestoreApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('grifo', {
                parent: 'entity',
                url: '/grifos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'databasestoreApp.grifo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/grifo/grifos.html',
                        controller: 'GrifoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('grifo');
                        $translatePartialLoader.addPart('enGrif');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('grifo.detail', {
                parent: 'entity',
                url: '/grifo/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'databasestoreApp.grifo.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/grifo/grifo-detail.html',
                        controller: 'GrifoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('grifo');
                        $translatePartialLoader.addPart('enGrif');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Grifo', function($stateParams, Grifo) {
                        return Grifo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('grifo.new', {
                parent: 'grifo',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/grifo/grifo-dialog.html',
                        controller: 'GrifoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    Tipo: null,
                                    Referencia: null,
                                    cantidad: null,
                                    comentario: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('grifo', null, { reload: true });
                    }, function() {
                        $state.go('grifo');
                    })
                }]
            })
            .state('grifo.edit', {
                parent: 'grifo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/grifo/grifo-dialog.html',
                        controller: 'GrifoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Grifo', function(Grifo) {
                                return Grifo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('grifo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('grifo.delete', {
                parent: 'grifo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/grifo/grifo-delete-dialog.html',
                        controller: 'GrifoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Grifo', function(Grifo) {
                                return Grifo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('grifo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
